package com.umc.DongnaeFriend.domain.account.book.controller;

import com.umc.DongnaeFriend.domain.account.book.dto.AccountBookDto;
import com.umc.DongnaeFriend.domain.account.book.service.AccountBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountBookController {

    private final AccountBookService accountBookService;

    @GetMapping("/budget")
    public ResponseEntity<AccountBookDto.BudgetResponse> getBudget(@RequestParam(value = "year", required = false) Integer year,
                                                                  @RequestParam(value = "month", required = false) Integer month){
        return ResponseEntity.status(HttpStatus.OK).body(accountBookService.getBudget(year, month));
    }

    @PostMapping("/budget")
    public ResponseEntity<?> createBudget(@RequestParam(value = "year", required = false) Integer year,
                             @RequestParam(value = "month", required = false) Integer month,
                             @RequestParam(value = "amount", required = false) Long budget){
        accountBookService.createBudget(year, month, budget);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/budget")
    public ResponseEntity<?> updateBudget(@RequestParam(value = "year", required = false) Integer year,
                             @RequestParam(value = "month", required = false) Integer month,
                             @RequestParam(value = "amount", required = false) Long budget){
        accountBookService.updateBudget(year, month, budget);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<AccountBookDto.AccountBookResponse> getAccountBook(@RequestParam(value = "year", required = false) Integer year,
                                                             @RequestParam(value = "month", required = false) Integer month){

        return ResponseEntity.status(HttpStatus.OK).body(accountBookService.getAccountBookResponse(year, month));
    }
}
