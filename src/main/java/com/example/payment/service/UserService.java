package com.example.payment.service;

import com.example.payment.dao.UserDao;
import com.example.payment.exeption.NotFoundException;
import com.example.payment.model.Transaction;
import com.example.payment.model.User;
import com.example.payment.model.Wallet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User create(User user) {
        return userDao.create(user);
    }

    public User find(String id) throws NotFoundException {
        User user = userDao.findById(id);
        if (user == null) {
            throw new NotFoundException();
        }
        return user;
    }

    public Wallet getWallet(String id) throws NotFoundException {
        User user = find(id);
        return user.getWallet();
    }

    public List<Transaction> getTransactions(String id) throws NotFoundException {
        User user = find(id);
        return user.getTransactions();
    }

    public Transaction buy(String id, int amount) throws NotFoundException {
        User user = find(id);
       Transaction transaction = user.buy(amount);
        if (transaction==null){
            throw new IllegalArgumentException("Account balance is not enough");
        }
        userDao.update(id, user);
        return transaction;
    }

    public User delete(String id) throws NotFoundException {
        find(id);
        return userDao.delete(id);
    }

    public Wallet chargeWallet(String id, int amount) throws NotFoundException {
        User user = find(id);
        int balance = user.chargeWallet(amount);
        if (balance==-1){
            throw new IllegalArgumentException("amount must be positive integers");
        }
        userDao.update(id,user);
        return user.getWallet();
    }
}