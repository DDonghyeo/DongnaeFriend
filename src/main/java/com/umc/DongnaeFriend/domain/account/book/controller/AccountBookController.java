package com.umc.DongnaeFriend.domain.account.book.controller;

import com.umc.DongnaeFriend.domain.account.book.dto.AccountBookDto;
import com.umc.DongnaeFriend.domain.account.book.service.AccountBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountBookController {

    private final AccountBookService accountBookService;

    @GetMapping("/budget")
    public ResponseEntity<AccountBookDto.BudgetResponse> getBudget(@Valid @RequestParam(value = "year") Integer year,
                                                                   @Valid @RequestParam(value = "month")
                                                                  @Max(value = 12, message = "12월까지만 입력해주세요") int month){
        return ResponseEntity.status(HttpStatus.OK).body(accountBookService.getBudget(year, month));
    }

    @PostMapping("/budget")
    public ResponseEntity<?> createBudget(@Valid @RequestParam(value = "year") Integer year,
                                          @Valid @RequestParam(value = "month")
                                            @Max(value = 12, message = "12월까지만 입력해주세요") int month,
                                          @Valid @RequestParam(value = "amount")
                                              @Min(value =0, message = "예산은 0원 이상이어야 합니다") long budget){
        accountBookService.createBudget(year, month, budget);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/budget")
    public ResponseEntity<?> updateBudget(@RequestParam(value = "year") Integer year,
                             @RequestParam(value = "month")
                                            @Max(value = 12, message = "12월까지만 입력해주세요") int month,
                             @RequestParam(value = "amount")
                                              @Min(value =0, message = "예산은 0원 이상이어야 합니다") long budget){
        accountBookService.updateBudget(year, month, budget);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<AccountBookDto.AccountBookResponse> getAccountBook(@RequestParam(value = "year") Integer year,
                                                             @RequestParam(value = "month")
                                                                @Max(value = 12, message = "12월까지만 입력해주세요") Integer month){

        return ResponseEntity.status(HttpStatus.OK).body(accountBookService.getAccountBookResponse(year, month));
    }
}
