package com.umc.DongnaeFriend.domain.account.book.controller;


import com.umc.DongnaeFriend.domain.account.book.dto.TransactionDto;
import com.umc.DongnaeFriend.domain.account.book.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDto.TransactionRequest request){
        transactionService.createTransaction(request);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<TransactionDto.TransactionListResponse> getTransaction(@RequestParam(value = "year", required = false) Integer year,
                                                                 @RequestParam(value = "month", required = false) Integer month,
                                                                 @RequestParam(value = "day", required = false) Integer day, Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.getTransactions(year, month, day, pageable));
    }

    @PutMapping
    public ResponseEntity<?> updateTransaction(@RequestBody TransactionDto.TransactionRequest requestDto,
                                  @RequestParam(value = "id", required = false) Long id){
        transactionService.updateTransaction(requestDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTransaction(@RequestParam(value = "id", required = false) Long id){
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
