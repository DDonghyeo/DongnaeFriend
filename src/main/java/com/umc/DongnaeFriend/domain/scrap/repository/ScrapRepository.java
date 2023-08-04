package com.umc.DongnaeFriend.domain.scrap.repository;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import com.umc.DongnaeFriend.domain.scrap.entity.Scrap;
import com.umc.DongnaeFriend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    @Query("SELECT u FROM User u WHERE u.id = :user_id")
    User findByUserId(@Param("user_id") Long user_id);
    @Query("SELECT sb FROM SharingBoard sb WHERE sb.id = :sharing_board_id")
    SharingBoard findBySharingBoardId(@Param("sharing_board_id") Long sharing_board_id);
    @Query("SELECT db FROM DongnaeBoard db WHERE db.id = :dongnae_board_id")
    DongnaeBoard findByDongnaeBoardId(@Param("dongnae_board_id") Long dongnae_board_id);
    @Query(value = "SELECT scrap.* FROM scrap WHERE scrap.sharing_board_id = :sharing_board_id", nativeQuery = true)
    Scrap findBySharingBoardId(@Param("sharing_board_id") SharingBoard sharing_board_id);
    @Query(value = "SELECT scrap.* FROM scrap WHERE scrap.dongnae_board_id = :dongnae_board_id", nativeQuery = true)
    Scrap findByDongnaeBoardId(@Param("dongnae_board_id") DongnaeBoard dongnae_board_id);
}
