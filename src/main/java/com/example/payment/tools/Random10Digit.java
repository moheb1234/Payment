package com.example.payment.tools;

public class Random10Digit {
    public static String create10Digit(){
        long digits = (long) (Math.random()*(9000000000L)+1000000000L);
        return digits+"";
    }
}
