package com.umc.DongnaeFriend.domain.account.book.controller;

import com.umc.DongnaeFriend.domain.account.book.dto.AccountBookDto;
import com.umc.DongnaeFriend.domain.account.book.service.AccountBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/budget")
    public void updateBudget(@RequestParam(value = "year", required = false) Integer year,
                             @RequestParam(value = "month", required = false) Integer month,
                             @RequestParam(value = "amount", required = false) Long budget){
        accountBookService.updateBudget(year, month, budget);
    }

    @GetMapping("/category")
    public List<AccountBookDto.AccountBookCategoryResponse> getTransactionAll(@RequestParam(value = "year", required = false) Integer year,
                                                                              @RequestParam(value = "month", required = false) Integer month){
        return null;
    }

    @GetMapping("/all")
    public AccountBookDto.AccountBookResponse getAccountBook(@RequestParam(value = "year", required = false) Integer year,
                                                             @RequestParam(value = "month", required = false) Integer month){

        return accountBookService.getAccountBookResponse(year, month);
    }
}
