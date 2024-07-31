package dev.jmv.account.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import dev.jmv.account.dto.AccountDTO;
import dev.jmv.account.entity.Account;
import dev.jmv.account.exception.RecordNotFoundException;
import dev.jmv.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static dev.jmv.account.util.Transformer.dtoToEntity;
import static dev.jmv.account.util.Transformer.entityToDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final RestTemplate restTemplate;

    public List<AccountDTO> getAllAccounts() throws Exception {
        log.info("Get All Accounts");
        return entityToDto(accountRepository.findAll());
    }

    public List<AccountDTO> createAllAccounts(List<AccountDTO> accountDTOS) {
        var accounts = accountRepository.saveAll(dtoToEntity(accountDTOS));
        return entityToDto(accounts);
    }

    public AccountDTO createAccounts(AccountDTO accountDTO) {
        var account = dtoToEntity(accountDTO);
        return entityToDto(accountRepository.save(account));
    }

    public void delete(AccountDTO accountDTO) {
        accountRepository.delete(dtoToEntity(accountDTO));
    }

    public void delete(int id) {
        accountRepository.deleteById(id);
    }

    public AccountDTO getById(int id) {
        return entityToDto(accountRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Record not Found")));
    }

    public void updateById(int id, AccountDTO accountDTO) throws RecordNotFoundException {
        accountRepository.findById(id).ifPresentOrElse(account -> {
            account.setAccountNumber(accountDTO.getAccountNumber());
            accountRepository.save(account);
        }, () -> {
            throw new RecordNotFoundException("Record Not Found");
        });
    }

    public List<AccountDTO> bulkUpload(MultipartFile file) throws IOException, CsvException, ExecutionException, InterruptedException {
        List<AccountDTO> accountDTOS = parseToAccountDTO(file);
        return entityToDto(encryptAndSave3(accountDTOS));
    }

    private static List<AccountDTO> parseToAccountDTO(MultipartFile file) throws IOException {
        Reader reader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReaderBuilder(reader).build();
        List<AccountDTO> accountDTOS = new CsvToBeanBuilder(csvReader).withType(AccountDTO.class).build().parse();
        return accountDTOS;
    }

    // Encrypt one by one and then saveAll
    private List<Account> encryptAndSave(List<AccountDTO> accountDTOS) {
        for (AccountDTO accountDTO : accountDTOS) {
            var encryptedAccountNumber = restTemplate.postForObject("http://localhost:9090/aes/encrypt", accountDTO.getAccountNumber(), String.class);
            accountDTO.setAccountNumber(encryptedAccountNumber);
        }
        return accountRepository.saveAll(dtoToEntity(accountDTOS));
    }

    // Encrypt & Save one by one
    private List<Account> encryptAndSave1(List<AccountDTO> accountDTOS) {
        List<Account> accounts = new ArrayList<>();
        for (AccountDTO accountDTO : accountDTOS) {
            var encryptedAccountNumber = restTemplate.postForObject("http://localhost:9090/aes/encrypt", accountDTO.getAccountNumber(), String.class);
            accountDTO.setAccountNumber(encryptedAccountNumber);
            accounts.add(accountRepository.save(dtoToEntity(accountDTO)));
        }
        return accounts;
    }

    // All encryption call in parallel
    private List<Account> encryptAndSave2(List<AccountDTO> accountDTOS) throws ExecutionException, InterruptedException {
        List<CompletableFuture<String>> futureList = new ArrayList<>();
        for (AccountDTO accountDTO : accountDTOS) {
            var future = CompletableFuture.supplyAsync(() -> restTemplate.postForObject("http://localhost:9090/aes/encrypt", accountDTO.getAccountNumber(), String.class));
            futureList.add(future);
        }

        for (int i = 0; i < accountDTOS.size(); i++) {
            accountDTOS.get(i).setAccountNumber(futureList.get(i).get());
        }
        return accountRepository.saveAll(dtoToEntity(accountDTOS));
    }

    // 1000 encryption call in parallel
    private List<Account> encryptAndSave3(List<AccountDTO> accountDTOS) throws ExecutionException, InterruptedException {

        double sets = (double) accountDTOS.size() / 1000;
        var loopLen = Math.ceil(sets);

        var start = 0;
        var end = 1000;

        while (loopLen > 0) {
            var accountDTOSListChunk = accountDTOS.subList(start, end);

            List<CompletableFuture<String>> futureList = accountDTOSListChunk.stream()
                    .map(accountDTO -> CompletableFuture.supplyAsync(() ->
                            restTemplate.postForObject("http://localhost:9090/aes/encrypt", accountDTO.getAccountNumber(), String.class)))
                    //.limit(1000)
                    .toList();

            for (int i = 0; i < 1000; i++) {
                accountDTOSListChunk.add(AccountDTO.builder().accountNumber(futureList.get(i).get()).accountStatus(accountDTOS.get(i).getAccountStatus()).build());
            }
            accountRepository.saveAll(dtoToEntity(accountDTOSListChunk));
            loopLen--;
        }
        return null;
    }

}
