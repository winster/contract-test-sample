package com.example.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMessageVerifier
public abstract class BaseTestClass {
    
    @Autowired
    ProducerApplication producerApplication;
    
    public void onBaseTestClassMethod() {
        producerApplication.publishEvent();
    }
}
