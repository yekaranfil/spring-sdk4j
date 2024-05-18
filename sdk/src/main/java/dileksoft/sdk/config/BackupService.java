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

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    @Value("${backup.databaseName}")
    private String DATABASE_NAME;

    @Value("${backup.serverName}")
    private String serverName;

    @Value("${backup.directory}")
    private String BACKUP_DIRECTORY;

    @Value("${telegram.groupId}")
    private String GROUP_ID; // Telegram group ID

    //@PostConstruct
    @Scheduled(cron = "0 59 20  * * ?") // Her gün gece 00:40'ta çalıştır
    // Her gün saat 00:00'da çalıştır
    public void performBackup() {
        try {
            System.out.println(DATABASE_NAME+", " + serverName + ", " + BACKUP_DIRECTORY + ", " + GROUP_ID + ", " + DATE_FORMAT);
            System.out.println("PostgreSQL yedek alma işlemi başlatıldı.");
            String backupDateTime = DATE_FORMAT.format(new Date());
            String backupFileName = String.format("wisitcard-tr_%s_%s.sql", DATABASE_NAME, backupDateTime);
            String backupFilePath = BACKUP_DIRECTORY + backupFileName;

            // PostgreSQL yedek alma komutu
            String[] command = {"bash", "-c", "sudo -u postgres pg_dump -U postgres -d " + DATABASE_NAME + " > " + backupFilePath};
            Process process = Runtime.getRuntime().exec(command);



            // Process'in tamamlanmasını bekle
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("PostgreSQL yedek alma işlemi tamamlandı.");
                sendBackupToTelegram(backupFilePath, DATABASE_NAME, backupDateTime);
            } else {
                System.err.println("PostgreSQL yedek alma işlemi başarısız oldu. Çıkış kodu: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    //TODO BAKILACAK
    private void replaceOldBackup(String newBackupPath) {
        // Önceki yedek dosyasını al
        File directory = new File(BACKUP_DIRECTORY);
        File[] files = directory.listFiles();
        if (files != null && files.length > 0) {
            // En yeni dosya alındıktan sonra diğer dosyaları kaydederken yeni yedeğin üzerine yazılması gerekiyor.
            File lastModifiedFile = files[0];
            for (int i = 1; i < files.length; i++) {
                if (files[i].lastModified() > lastModifiedFile.lastModified()) {
                    lastModifiedFile = files[i];
                }
            }

            // Yeni yedekle yer değiştir
            try {
                File newBackupFile = new File(newBackupPath);
                File oldBackupFile = new File(lastModifiedFile.getAbsolutePath());

                // Yer değiştirme işlemi
                if (newBackupFile.exists() && newBackupFile.isFile()) {
                    // Yeni yedeği sil
                    newBackupFile.delete();
                }

                // Eski yedekle yeni yeri değiştir
                if (oldBackupFile.exists() && oldBackupFile.isFile()) {
                    oldBackupFile.renameTo(newBackupFile);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendBackupToTelegram(String backupFilePath, String databaseName, String backupDateTime) {
        // Yedek dosyasını Telegram'a gönder
        TelegramBot telegramBot = new TelegramBot();
        String message = String.format("Yedek alınan sunucu adı: %s\nYedek alınan tarih: %s\nYedek alınan veritabanı: %s", serverName, backupDateTime, databaseName);
        telegramBot.sendDocument(GROUP_ID, backupFilePath,message);

        // Yedek dosyasını sil


        // Yedek dosyasını başka bir yere taşı
        String newBackupPath = BACKUP_DIRECTORY + String.format("wisitcard-tr_%s_%s.sql", databaseName, backupDateTime);
        //replaceOldBackup(newBackupPath);
    }
}
