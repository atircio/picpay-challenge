package com.atircio.pickpay.resources;

import com.atircio.pickpay.dtos.TransactionDto;
import com.atircio.pickpay.dtos.TransferMoneyRequestDto;
import com.atircio.pickpay.entities.enums.TransactionStatus;
import com.atircio.pickpay.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionResource {

    private final TransactionService transactionService;

    public TransactionResource(TransactionService transactionService){
        this.transactionService = transactionService;
    }


    @PostMapping("/transferMoneyByCpf")
    public ResponseEntity<TransactionDto> sendMoney(
             @RequestBody TransferMoneyRequestDto requestDto,
            @RequestHeader("X-Sender-CPF") String senderCpf){
        TransactionDto transaction = transactionService.sendMoney(requestDto, senderCpf);

        return ResponseEntity.status(HttpStatus.OK).body(transaction);

    }
}
