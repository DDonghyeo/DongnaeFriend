package com.umc.DongnaeFriend.domain.account.book.controller;

import com.umc.DongnaeFriend.domain.account.book.dto.AccountBookDto;
import com.umc.DongnaeFriend.domain.account.book.service.AccountBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountBookController {

    private final AccountBookService accountBookService;

    @GetMapping("/budget")
    public AccountBookDto.BudgetResponse getBudget(@RequestParam(value = "year", required = false) Integer year,
                                                    @RequestParam(value = "month", required = false) Integer month){
        return accountBookService.getBudget(year, month);
    }

    @PostMapping("/budget")
    public void createBudget(@RequestParam(value = "year", required = false) Integer year,
                             @RequestParam(value = "month", required = false) Integer month,
                             @RequestParam(value = "amount", required = false) Long budget){
        accountBookService.createBudget(year, month, budget);
    }


}
