package com.umc.DongnaeFriend.domain.account.book.repository.memo;

import com.umc.DongnaeFriend.domain.account.book.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long>, MemoRepositoryCustom{
    Optional<Memo> findByAccountBookId(Long accountBookId);
    @Query("select m from Memo m where m.accountBook.id = :accountBookId")
    List<Memo> findMemoListByAccountBookId(@Param("accountBookId") Long accountBookId);
}
