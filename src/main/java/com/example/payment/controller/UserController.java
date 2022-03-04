package com.example.payment.controller;

import com.example.payment.exeption.NotFoundException;
import com.example.payment.model.Transaction;
import com.example.payment.model.User;
import com.example.payment.model.Wallet;
import com.example.payment.service.UserService;
import com.example.payment.tools.Random10Digit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/payment/user")
    public ResponseEntity<User> create(@RequestBody User user) {
        user.setTransactions(new ArrayList<>());
        user.setWallet(new Wallet(Random10Digit.create10Digit(), 0));
        return new ResponseEntity<>(service.create(user), HttpStatus.CREATED);
    }

    @GetMapping("payment/user/{id}")
    public ResponseEntity<User> find(@PathVariable String id) throws NotFoundException {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }

    @GetMapping("payment/user/{id}/wallet")
    public ResponseEntity<Wallet> getWallet(@PathVariable String id) throws NotFoundException {
        return new ResponseEntity<>(service.getWallet(id), HttpStatus.OK);
    }

    @GetMapping("payment/user/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String id) throws NotFoundException {
        return new ResponseEntity<>(service.getTransactions(id), HttpStatus.OK);
    }

    @PutMapping("payment/user/{id}")
    public ResponseEntity<Transaction> buy(@PathVariable String id, @RequestParam int amount) throws NotFoundException {
        return new ResponseEntity<>(service.buy(id,amount), HttpStatus.OK);
    }

    @PutMapping("payment/user/{id}/charge")
    public ResponseEntity<Wallet> chargeWallet(@PathVariable String id, @RequestParam int amount) throws NotFoundException {
        return new ResponseEntity<>(service.chargeWallet(id, amount), HttpStatus.OK);
    }

    @DeleteMapping("payment/user/{id}")
    public ResponseEntity<User> delete(@PathVariable String id) throws NotFoundException {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
