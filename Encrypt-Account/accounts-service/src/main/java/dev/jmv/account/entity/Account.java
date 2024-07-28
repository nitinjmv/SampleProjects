package dev.jmv.account.entity;

import dev.jmv.account.dto.AccountStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ACCOUNT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String accountNumber;
    private AccountStatus accountStatus;
    private String accountHolder;
}
