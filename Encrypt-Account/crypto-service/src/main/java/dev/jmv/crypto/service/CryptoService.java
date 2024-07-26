package dev.jmv.crypto.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CryptoService {

    public String encrypt(String plainText) throws InterruptedException {
        Thread.sleep(100);
        return plainText + Math.random();
    }

    public String decrypt(String cipherText) throws InterruptedException {
        Thread.sleep(100);
        return cipherText.replaceAll("[0-9]", "");
    }
}
