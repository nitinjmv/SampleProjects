package dev.jmv.account.util;

import dev.jmv.account.dto.AccountDTO;
import dev.jmv.account.entity.Account;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class Transformer {
    public static List<Account> dtoToEntity(List<AccountDTO> accountDTOS) {
        log.debug("Converting DTO to Entity");
        var accountList = new ArrayList<Account>();

        accountDTOS.forEach(
                account -> {
                    accountList.add(
                            Account.builder()
                                    .id(account.getId())
                                    .title(account.getTitle())
                                    .build());
                }
        );
        return accountList;
    }

    public static AccountDTO entityToDto(Account account) {
        log.debug("Converting Entity to DTO");

        return AccountDTO.builder()
                .id(account.getId())
                .title(account.getTitle())
                .build();
    }

    public static Account dtoToEntity(AccountDTO accountDTO) {
        log.debug("Converting DTO to Entity");
        return Account.builder()
                .id(accountDTO.getId())
                .title(accountDTO.getTitle())
                .build();
    }

    public static List<AccountDTO> entityToDto(List<Account> accounts) {
        log.debug("Converting Entity to DTO");
        var accountDTOList = new ArrayList<AccountDTO>();

        accounts.forEach(
                account -> {
                    accountDTOList.add(
                            AccountDTO.builder()
                                    .id(account.getId())
                                    .title(account.getTitle())
                                    .build());
                }
        );
        return accountDTOList;
    }
}
