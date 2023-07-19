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

    @Query(value = "select * from dongnae_board where title like %?1%  or content like %?1% and category = ?2 ;", nativeQuery = true)
    public List<DongnaeBoard> findByKeyword(String keyword, String category);

    @Query(nativeQuery = true, value = "SELECT * FROM dongnae_board WHERE title like %:keyword% or content like  %:keyword% ")
    public List<DongnaeBoard> searchBoard(@Param("keyword") String keyword);

}
