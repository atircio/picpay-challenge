package com.atircio.pickpay.exceptions;

public class TransactionFailedException extends RuntimeException {
    public TransactionFailedException(String s) {
        super(s);
    }

    public TransactionFailedException() {
        super("Transaction failed due to an internal error.");
    }
}
