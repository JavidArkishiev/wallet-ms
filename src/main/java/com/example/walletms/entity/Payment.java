package com.example.walletms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "paymenties",
        schema = "wallet_ms")
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID paymentId;
    @Column(nullable = false)
    private String phoneNumber;
    private BigDecimal amount;
    private String paymentMethod;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Transaction transaction;
}
