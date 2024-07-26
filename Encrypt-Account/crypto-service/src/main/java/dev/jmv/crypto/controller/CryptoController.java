package dev.jmv.crypto.controller;

import dev.jmv.crypto.dto.APIResponse;
import dev.jmv.crypto.service.CryptoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.rmi.MarshalException;

@Slf4j
@RestController
@RequestMapping("aes")
@RequiredArgsConstructor
public class CryptoController {

    private final CryptoService cryptoService;

    @PostMapping(value = "encrypt",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> encrypt(@RequestBody String plainText) throws InterruptedException {

        var response = cryptoService.encrypt(plainText);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "decrypt",
            consumes= MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> decrypt(@RequestBody String cipherText) throws InterruptedException {

        var response = cryptoService.decrypt(cipherText);
        return ResponseEntity.ok(response);
    }

}
