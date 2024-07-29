package dev.jmv.account.dto;


import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {

    private Integer id;

    @CsvBindByName(column = "account_number")
    private String accountNumber;

    @CsvBindByName(column = "account_status")
    private AccountStatus accountStatus;

}
