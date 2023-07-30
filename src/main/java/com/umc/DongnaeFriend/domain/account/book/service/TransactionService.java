package com.umc.DongnaeFriend.domain.account.book.service;

import com.umc.DongnaeFriend.domain.account.book.dto.TransactionDto;
import com.umc.DongnaeFriend.domain.account.book.entity.AccountBook;
import com.umc.DongnaeFriend.domain.account.book.entity.Transaction;
import com.umc.DongnaeFriend.domain.account.book.repository.accountBook.AccountBookRepository;
import com.umc.DongnaeFriend.domain.account.book.repository.transaction.TransactionRepository;
import com.umc.DongnaeFriend.global.exception.CustomException;
import com.umc.DongnaeFriend.global.exception.ErrorCode;
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

    private final TransactionRepository transactionRepository;
    private final AccountBookRepository accountBookRepository;

    // 지출 또는 수입 내역 추가
    @Transactional
    public void createTransaction(TransactionDto.TransactionRequest request){

        AccountBook accountBook = findTarget(request.getYear(), request.getMonth());

        transactionRepository.save(request.toEntity(accountBook));

        // Type=1이면 수입, 0이면 지출
        if(request.getType()==1){
            accountBookRepository.updateAccountBookIncome(accountBook.getId(), request.getPrice());
        }else{
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

        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_CONTENT_FOUND));
        AccountBook accountBook = findTarget(transaction.getYear(), transaction.getMonth());


        if(transaction.getType()==1){
            accountBookRepository.updateAccountBookIncomeDelete(accountBook.getId(), transaction.getPrice());
        }else{
            accountBookRepository.updateAccountBookExpenditureDelete(accountBook.getId(), transaction.getPrice());
        }
        transactionRepository.deleteById(transaction.getId());
    }

    // 지출 또는 수입 내역 수정
    @Transactional
    public void updateTransaction(TransactionDto.TransactionRequest request, Long transactionId){
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_CONTENT_FOUND));
        AccountBook accountBook = findTarget(transaction.getYear(), transaction.getMonth());

        Transaction updateTrans = request.toEntity(accountBook);
        Long priceGap = updateTrans.getPrice()-transaction.getPrice();

        if(transaction.getType()==1){
            accountBookRepository.updateAccountBookIncomeEdit(accountBook.getId(), priceGap);
        }else{
            accountBookRepository.updateAccountBookExpenditureEdit(accountBook.getId(), priceGap);
        }

        transaction.updateTransaction(request, accountBook);
    }

    private AccountBook findTarget(Integer year, Integer month){
        return accountBookRepository.findByYearAndMonth(year, month)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_CONTENT_FOUND));
    }
}
