package com.umc.DongnaeFriend.domain.account.book.service;

import com.umc.DongnaeFriend.domain.account.book.dto.TransactionDto;
import com.umc.DongnaeFriend.domain.account.book.entity.AccountBook;
import com.umc.DongnaeFriend.domain.account.book.entity.Transaction;
import com.umc.DongnaeFriend.domain.account.book.repository.accountBook.AccountBookRepository;
import com.umc.DongnaeFriend.domain.account.book.repository.transaction.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    /**
     * 월별 가계부 지출 및 수입 업데이트 시 지출(예산) 총액이 0보다 작아지면 예외 발생하게 만들기
     * 유저 권한 확인 필요
     */

    private final TransactionRepository transactionRepository;
    private final AccountBookRepository accountBookRepository;

    // 지출 또는 수입 내역 추가
    @Transactional
    public void createTransaction(TransactionDto.TransactionRequest request){

        log.info("Year : " + request.getYear() + " Month : " + request.getMonth());
        AccountBook accountBook = findTarget(request.getYear(), request.getMonth());

        transactionRepository.save(request.toEntity(accountBook));

        // AccountBook income, expenditure 값 변경

        if(request.getType()==1){
            log.info("Price 추가 : " + request.getPrice());

            accountBookRepository.updateAccountBookIncome(accountBook.getId(), request.getPrice());
        }else{
            log.info("Price 추가 : " + request.getPrice());

            accountBookRepository.updateAccountBookExpenditure(accountBook.getId(), request.getPrice());
        }
    }

    // 지출 또는 수입 내역 조회
    public TransactionDto.TransactionListResponse getTransactions(Integer year, Integer month, Integer day, Pageable pageable){

        List<Transaction> transactionList  = transactionRepository.findByYearAndMonth(year, month, day, pageable);
        return TransactionDto.TransactionListResponse.of(transactionList);
    }

    // 지출 또는 수입 내역 삭제
    @Transactional
    public void deleteTransaction(Long transactionId){

        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();
        AccountBook accountBook = findTarget(transaction.getYear(), transaction.getMonth());

        // AccountBook income, expenditure 값이 변경되어야 함.
        if(transaction.getType()==1){
            log.info("Price 삭제: " + transaction.getPrice());
            accountBookRepository.updateAccountBookIncomeDelete(accountBook.getId(), transaction.getPrice());
        }else{
            log.info("Price 삭제 : " + transaction.getPrice());

            accountBookRepository.updateAccountBookExpenditureDelete(accountBook.getId(), transaction.getPrice());
        }
        transactionRepository.deleteById(transaction.getId());
    }

    // 지출 또는 수입 내역 수정
    @Transactional
    public void updateTransaction(TransactionDto.TransactionRequest request, Long transactionId){
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow();
        AccountBook accountBook = findTarget(transaction.getYear(), transaction.getMonth());

        Transaction updateTrans = request.toEntity(accountBook);
        Long priceGap = updateTrans.getPrice()-transaction.getPrice();
        log.info("priceGap : " + priceGap);

        if(transaction.getType()==1){
            accountBookRepository.updateAccountBookIncomeEdit(accountBook.getId(), priceGap);
        }else{
            accountBookRepository.updateAccountBookExpenditureEdit(accountBook.getId(), priceGap);
        }

        transaction.updateTransaction(request, accountBook);
    }

    private AccountBook findTarget(Integer year, Integer month){
        return accountBookRepository.findByYearAndMonth(year, month).orElseThrow();
    }
}
