package com.atircio.pickpay.exceptions;

public class DuplicateEmailException extends RuntimeException
{
    public DuplicateEmailException(String msg){
        super(msg);
    }
}
