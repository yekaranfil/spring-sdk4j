# spring-sdk4j
## BU SDK(software development kit) Spring bazlı back-end projeleri için oluşturulmuştur
## LİSANS 
```
Amme Hizmetleri License 2.0 (AHL-2.0)
```
Şaka Şaka Hak hukuka gerek yoktur. İstediğiniz gibi kullanabilirsiniz. 
Tamamen Amme Hizmetidir. Open Source olsun bizim olsun.

### Kullanım
- Projeyi indirin
- Projeyi maven ile import edin
- Projeyi çalıştırın
- Projeyi test etmek için http://localhost:8080/swagger-ui.html adresine gidin

### KURULUM 
- Projeyi indirin
- Projeyi maven ile clean edin
- Application.yml dosyasını düzenleyin

Projede Tüm paketler önceden hazır şekilde oluşturulmuş ve bazı paketlerin içerisinde önemli
sınıflar bulunmaktadır. Bu sınıfların bazıları aşağıdaki gibidir

-- Backup Service -> Veritabanı yedekleme işlemleri için kullanılır
-- JWT Service -> JWT token oluşturma ve kontrol işlemleri için kullanılır
-- Telegram Bot -> Telegram bot işlemleri için kullanılır
-- WebConfig -> Web konfigürasyon işlemleri için kullanılır
-- FastTSID -> FastTSID oluşturma işlemleri için kullanılır

### Application.yml
```yml
Bu dosya içerisinde önemli olarak düzenlenmesi gereken kısımlar aşağıdaki gibidir
```

```yml
- SERTİFİKA KURULUMU İÇİN -
Bu kısımda eğer SSL sertifikası istiyorsak true işaretlemeli ve dosyaların yollarını belirtmeliyiz
    enabled: false
    certificate: ##classpath:sertifika.pem  sertifika crt dosyas?
    certificate-private-key: ##classpath:sertifikakey.pem sertifika pem dosyas?
- VERİTABANI KONFIGÜRASYONU İÇİN -
# Bu kısımda veritabanı konfigürasyonu yapılmalıdır
    datasource:
        url: jdbc:mysql://localhost:3306/bu?useSSL=false&serverTimezone=UTC
        username: root
        password: root
        driver-class-name: com.mysql.cj.jdbc.Driver
- JWT KONFIGÜRASYONU İÇİN -
# Bu kısımda jwt konfigürasyonu yapılmalıdır
## JWT Login işlemi için kullanıcı adı ve şifre
    security:
      username: "username"
      password: "password"
- SWAGGER KONFIGÜRASYONU İÇİN -
# Bu kısımda swagger konfigürasyonu yapılmalıdır
    swagger:
        title: BU API
        description: BU API
        version: 1.0
        terms-of-service-url: http://www.bu.com
        contact:
            name: BU
            url: http://www.bu.com
            email:
- BACKUP KONFIGÜRASYONU İÇİN -
#Bu kısımda backup konfigürasyonu yapılmalıdır
    backup:
      databaseName: "database_name"
      serverName: "ServerName-TR | ip"
      directory: "src/main/resources/sqlbackups/"
    telegram:
      username: "botUsername"
      token: "botToken"
      groupId: "botChatId"

```

## Bu adımları takip ederek projeyi çalıştırabilir ve test edebilirsiniz

Bu işlemler sonucunda hızlı bir şekilde bir spring projesi taslağını oluşturmuş olacaksınız
Ardından modellerinizi, Dto'larınızı ve servislerinizi oluşturarak projenizi geliştirebilirsiniz

### Geliştirici
- [YUSUF EMRE KARANFİL] -> github.com/yekaranfil

- iletişim -> y.emrekaranfil@gmail.com, yekaranfil@dileksoft.com
- [Dileksoft Yazılım ve Bilişim Hizmetleri] -> dileksoft.com
```
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

```
