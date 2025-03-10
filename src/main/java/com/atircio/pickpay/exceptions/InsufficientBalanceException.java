package com.atircio.pickpay.exceptions;

public class InsufficientBalanceException extends RuntimeException
{
    public InsufficientBalanceException(String msg){
        super(msg);
    }
}
