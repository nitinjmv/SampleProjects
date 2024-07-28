package dev.jmv.account.controller;

import dev.jmv.account.dto.APIResponse;
import dev.jmv.account.dto.AccountDTO;
import dev.jmv.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static dev.jmv.account.Constants.ACCOUNTS_ENDPOINT;

@Slf4j
@RestController
@RequestMapping(ACCOUNTS_ENDPOINT)
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse> getAllAccounts() throws Exception {

        var response = accountService.getAllAccounts();

        return ResponseEntity.ok(
                APIResponse.builder()
                        .httpStatusCode(HttpStatus.OK.value())
                        .data(response)
                        .resultCount(response.size())
                        .build()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<APIResponse> getById(@PathVariable int id) {
        var response = accountService.getById(id);
        return ResponseEntity.ok(
                APIResponse.builder()
                        .httpStatusCode(HttpStatus.OK.value())
                        .data(response)
                        .build()
        );
    }

    @PostMapping(value = "create",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse> createAccounts(@RequestBody AccountDTO accountDTO) {
        var response = accountService.createAccounts(accountDTO);

        return new ResponseEntity<>(
                APIResponse.builder()
                        .httpStatusCode(HttpStatus.CREATED.value())
                        .data(response)
                        .build(),
                HttpStatus.CREATED
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponse> createAllAccounts(@RequestBody List<AccountDTO> accountDTOS) {

        var response = accountService.createAllAccounts(accountDTOS);

        return ResponseEntity.ok(
                APIResponse.builder()
                        .httpStatusCode(HttpStatus.CREATED.value())
                        .data(response)
                        .resultCount(response.size())
                        .build()
        );
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAccount(@RequestBody AccountDTO accountDTO) {
        accountService.delete(accountDTO);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAccountById(@PathVariable int id) {
        accountService.delete(id);
        return ResponseEntity.accepted().build();
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<APIResponse> updateById(@PathVariable int id, @RequestBody AccountDTO accountDTO) {
        accountService.updateById(id, accountDTO);
        return ResponseEntity.accepted().build();
    }
}
