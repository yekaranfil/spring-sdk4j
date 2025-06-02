/*
 *
 *  *
 *  *  *
 *  *  *  *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *  *  *  *
 *  *  *  *  Copyright (C) 2023 Dileksoft LLC  - All Rights Reserved.
 *  *  *  *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  *  *  *  Proprietary and confidential.
 *  *  *  *
 *  *  *  *  Written by Yusuf E. Karanfil <yekaranfil@dileksoft.com>, May 2024
 *  *  *
 *  *
 *
 */

package dileksoft.sdk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@EnableScheduling
public class BackupService {

    private final SCPUploader SCPUploader;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    @Value("${backup.databaseName}")
    private String DATABASE_NAME;

    @Value("${backup.serverName}")
    private String SERVER_NAME;

    @Value("${backup.directory}")
    private String BACKUP_DIRECTORY;

    @Value("${backup.serverIp}")
    private String SERVER_IP;

    @Value("${telegram.groupId}")
    private String GROUP_ID; //// Telegram group ID

    @Value("${backup.host}")//
    private String SSH_HOST;

    @Value("${backup.port}")//
    private int SSH_PORT;

    @Value("${backup.username}")//
    private String SSH_USER;

    @Value("${backup.password}")//
    private String SSH_PASSWORD;

    @Value("${backup.remotePath}")
    private String REMOTE_PATH;

    public BackupService(SCPUploader scpUploader) {
        SCPUploader = scpUploader;
    }

    private void deleteOldBackups(String remotePath) {
        try {
            // Local sqlbackups klasörünü temizle (en yeni dosya hariç)
            String localBackupPath = BACKUP_DIRECTORY;
            String[] localCommand = {
                    "bash",
                    "-c",
                    String.format(
                            "cd %s && ls -t *.sql | tail -n +2 | xargs -r rm --",
                            localBackupPath
                    )
            };

            Process localProcess = Runtime.getRuntime().exec(localCommand);
            int localExitCode = localProcess.waitFor();

            if (localExitCode == 0) {
                System.out.println("En yeni dosya dışındaki local yedekler başarıyla silindi.");
            } else {
                System.err.println("Local yedekler silinirken hata oluştu. Çıkış kodu: " + localExitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Local yedekler silinirken hata oluştu: " + e.getMessage());
        }
    }
    //@PostConstruct
    @Scheduled(cron = "0 01 00  * * ?") // Her gün gece 00:40'ta çalıştır
    // Her gün saat 00:00'da çalıştır
    public String performBackup() {
        try {
            System.out.println(DATABASE_NAME+", " + SERVER_NAME + ", " + BACKUP_DIRECTORY + ", " + GROUP_ID + ", " + DATE_FORMAT);
            System.out.println("PostgreSQL yedek alma işlemi başlatıldı.");
            String backupDateTime = DATE_FORMAT.format(new Date());
            String backupFileName = String.format(SERVER_NAME+"_%s_%s.sql", DATABASE_NAME, backupDateTime);
            String backupFilePath = BACKUP_DIRECTORY + backupFileName;

            // PostgreSQL yedek alma komutu
            String[] command = {"bash", "-c", "sudo -u postgres pg_dump -U postgres -d " + DATABASE_NAME + " > " + backupFilePath};
            deleteOldBackups(BACKUP_DIRECTORY);
            Process process = Runtime.getRuntime().exec(command);



            // Process'in tamamlanmasını bekle
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("PostgreSQL yedek alma işlemi tamamlandı.");
                sendBackupToTelegram(backupFilePath, backupDateTime,null);

                //dosyayı karşı sunucuya upload et


              /*  try {
                    String backupReturner = SCPUploader.uploadFile(SSH_HOST,SSH_PORT ,SSH_USER, SSH_PASSWORD, backupFilePath, REMOTE_PATH + backupFileName);
                    if (backupReturner.equals("0")){
                        sendBackupToTelegram(backupFilePath, backupDateTime, null);
                    } else if(backupReturner.equals("1")){
                        sendBackupToTelegram(backupFilePath, backupDateTime, "Dosya sunucuya yüklenirken bir hata oluştu: " + backupReturner);
                        System.err.println("Dosya sunucuya yüklenirken bir hata oluştu: ");
                    }
                } catch (Exception e) {
                    System.err.println("Dosya sunucuya yüklenirken bir hata oluştu: " + e.getMessage());
                    // Hata durumunu yönetin
                }*/
                return "PostgreSQL yedek alma işlemi tamamlandı.";

            } else {
                System.err.println("PostgreSQL yedek alma işlemi başarısız oldu. Çıkış kodu: " + exitCode);
                return "PostgreSQL yedek alma işlemi başarısız oldu. Çıkış kodu: " + exitCode;

            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "PostgreSQL yedek alma işlemi başarısız oldu. "+ e.getMessage();
        }
    }

    private void sendBackupToTelegram(String backupFilePath, String backupDateTime, String responseMessage) {
        String date = backupDateTime.split("_")[0];
        String time = backupDateTime.split("_")[1];

        String message = String.format(
                "📁 *Yedek Alınan Sunucu Bilgileri*\n" +
                        "\\- *Sunucu Adı:* %s\n" +
                        "\\- *Sunucu IP Adresi:* %s\n\n" +
                        "📅 *Yedek Alınan Tarih:* %s\n" +
                        "🕒 *Yedek Alınan Saat:* %s\n\n" +
                        "💾 *Yedek Alınan Veritabanı:* %s\n\n" +
                        "📥 *Yedek Dosyası Bilgileri*\n" +
                        "\\- *Yedek Dosyası Adresi:* %s\n" +
                        "\\- *Yedek Dosyası Yolu:* %s\n" +
                        "\\- *Dosya Adı:* %s",
                escapeMarkdownV2(SERVER_NAME),
                escapeMarkdownV2(SERVER_IP),
                escapeMarkdownV2(date),
                escapeMarkdownV2(time),
                escapeMarkdownV2(DATABASE_NAME),
                escapeMarkdownV2(SSH_HOST),
                escapeMarkdownV2(REMOTE_PATH),
                escapeMarkdownV2(new File(backupFilePath).getName())
        );
        if (responseMessage != null && !responseMessage.isEmpty()) {
            message = responseMessage;
        }
        TelegramBot telegramBot = new TelegramBot();
        telegramBot.sendMessage(message);

    }
    private String escapeMarkdownV2(String text) {
        String[] charsToEscape = {"_", "*", "[", "]", "(", ")", "~", "`", ">", "#", "+", "-", "=", "|", "{", "}", ".", "!"};
        for (String ch : charsToEscape) {
            text = text.replace(ch, "\\" + ch);
        }
        return text;
    }

}
