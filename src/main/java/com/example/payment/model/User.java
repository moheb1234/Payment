package com.example.payment.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonBinaryType.class)
})
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(schema = "payment_schema", name = "user")
public class User {
    @Id
    private String id;
    @Type(type = "json")
    private List<Transaction> transactions;
    @Type(type = "json")
    private Wallet wallet;

    public User() {
    }

    public int chargeWallet(int amount) {
        if (amount <= 0) {
            return -1;
        }
        wallet.setAccountBalance(wallet.getAccountBalance() + amount);
        return wallet.getAccountBalance();
    }

    public Transaction buy(int amount) {
        if (amount > wallet.getAccountBalance()) {
            return null;
        }
        Transaction transaction = new Transaction(amount);
        transactions.add(transaction);
        wallet.setAccountBalance(wallet.getAccountBalance() - amount);
        return transaction;
    }
}
