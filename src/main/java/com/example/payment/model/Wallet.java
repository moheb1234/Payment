package com.example.payment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wallet {
    String walletNumber;
    int accountBalance;

    public Wallet(String walletNumber, int accountBalance) {
        this.walletNumber = walletNumber;
        this.accountBalance = accountBalance;
    }
}