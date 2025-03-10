package com.atircio.pickpay.exceptions;

public class TransactionFailedException extends RuntimeException {
    public TransactionFailedException(String s) {
        super(s);
    }
}
