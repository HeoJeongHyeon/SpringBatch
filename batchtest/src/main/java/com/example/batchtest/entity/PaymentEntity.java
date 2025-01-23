package com.example.batchtest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private int Amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.AWAITING_PAYMENT;


}