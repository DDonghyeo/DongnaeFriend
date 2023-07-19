package com.umc.DongnaeFriend.domain.dongnae.respository;

import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface DongnaeBoardRepository extends JpaRepository<DongnaeBoard, Long> {

    @Query(value = "select * from dongnae_board where title like %?1%  or content like %?1% and category = ?2 ORDER BY created_at DESC;", nativeQuery = true)
    List<DongnaeBoard> findByKeywordOrderByCreatedAt(String keyword, String category);

    @Query(value = "SELECT dongnae_board.*, COUNT(dongnae_sympathy.dongnae_board_id) AS cnt FROM dongnae_board\n" +
            "LEFT JOIN dongnae_sympathy ON  dongnae_board.dongnae_board_id = dongnae_sympathy.dongnae_board_id\n" +
            "WHERE (dongnae_board.title LIKE %?1% OR dongnae_board.content LIKE %?2%)\n" +
            "AND dongnae_board.category = ?2 GROUP BY dongnae_board.dongnae_board_id ORDER BY cnt DESC ;", nativeQuery = true)
    List<DongnaeBoard> findByKeywordOrderByLikes(String keyword, String category);



}
