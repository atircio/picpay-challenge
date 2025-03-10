package com.atircio.pickpay.exceptions;

public class UnauthorizedTransactionException extends RuntimeException {
    public UnauthorizedTransactionException(String s) {
        super(s);
    }
}
