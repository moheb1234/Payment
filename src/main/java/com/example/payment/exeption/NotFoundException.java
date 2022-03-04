package com.example.payment.exeption;

public class NotFoundException extends Exception {

    public NotFoundException() {
        super("user not founded");
    }
}
