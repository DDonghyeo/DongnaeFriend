package com.umc.DongnaeFriend.domain.account.book.controller;


import com.umc.DongnaeFriend.domain.account.book.dto.TransactionDto;
import com.umc.DongnaeFriend.domain.account.book.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public void createTransaction(@RequestBody TransactionDto.TransactionRequest request){
        transactionService.createTransaction(request);
    }

    @GetMapping
    public TransactionDto.TransactionListResponse getTransaction(@RequestParam(value = "year", required = false) Integer year,
                                                             @RequestParam(value = "month", required = false) Integer month){
        return transactionService.getTransactions(year, month);
    }

    @PutMapping
    public void updateTransaction(@RequestBody TransactionDto.TransactionRequest requestDto,
                                  @RequestParam(value = "id", required = false) Long id){
        transactionService.updateTransaction(requestDto, id);
    }

    @DeleteMapping
    public void deleteTransaction(@RequestParam(value = "id", required = false) Long id){
        transactionService.deleteTransaction(id);
    }
}
