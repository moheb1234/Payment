package com.example.payment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Transaction {
    private int referenceCode;
    private int amount;
    private Date date;

    public Transaction(int amount) {
        this.amount = amount;
        referenceCode= (int) (Math.random()*(99999)+10000);
        date = new Date();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "referenceCode=" + referenceCode +
                ", amount=" + amount +
                '}';
    }
}