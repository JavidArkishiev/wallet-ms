package com.example.walletms.entity;

import com.example.walletms.enums.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "balances",
        schema = "wallet_ms")
@Entity
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID balanceId;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private BigDecimal totalBalance;
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private LocalDateTime createAt;

    @OneToMany(mappedBy = "balance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

}
