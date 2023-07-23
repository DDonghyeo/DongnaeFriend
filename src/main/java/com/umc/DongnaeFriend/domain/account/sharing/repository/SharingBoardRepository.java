package com.umc.DongnaeFriend.domain.account.book.repository;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SharingBoardRepository extends JpaRepository <SharingBoard,Long> {

    @Query(value = "SELECT sharing_board.*, COUNT(sharing_sympathy.sharing_board_id) AS like FROM sharing_board\n" +
            "LEFT JOIN sharing_sympathy ON  sharing_board.sharing_board_id = sharing_sympathy.sharing_board_id\n" +
            "WHERE (sharing_board.title LIKE %?1% OR sharing_board.content LIKE %?2%)\n" +
            "AND sharing_board.category = ?2 GROUP BY sharing_board.sharing_board_id ;", nativeQuery = true)
    List<SharingBoard> findByKeywordAAndCategory(String keyword, String category, Pageable pageable);

}
