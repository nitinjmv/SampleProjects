package dev.jmv.account.service;

import dev.jmv.account.dto.AccountDTO;
import dev.jmv.account.exception.RecordNotFoundException;
import dev.jmv.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static dev.jmv.account.util.Transformer.dtoToEntity;
import static dev.jmv.account.util.Transformer.entityToDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

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
            account.setTitle(accountDTO.getTitle());
            accountRepository.save(account);
        }, () -> {
            throw new RecordNotFoundException("Record Not Found");
        });
    }
}
