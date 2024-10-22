package com.app.tocknock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;


import javax.annotation.PostConstruct;
import java.io.IOException;


/*
    서버가 파이어베이스 서버임을 인증

    @Component 빈으로 등록되고
    @PostConstruct WAS가 실행되면서 빈객체 생성, 의존성 주입이 되어 초기화
*/

@Component
@Slf4j
public class FCMInitializer {

    @Value("${fcm.firebase_config_path}")
    private String firebaseConfigPath;


    @PostConstruct //빈 객체가 생성되고 의존성 주입이 완료된 후에 초기화가 실행될 수 있도록 @PostConstruct 설정
    public void initialize() {
        try {
            //log.debug("FCMInitializer processed!");
            //log.info("FCM path: " + FIREBASE_CONFIG_PATH);

            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream());

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(googleCredentials)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                log.info("Firebase application is empty..");
                FirebaseApp.initializeApp(options);
                log.info("Firebase application has been initialized");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


}


