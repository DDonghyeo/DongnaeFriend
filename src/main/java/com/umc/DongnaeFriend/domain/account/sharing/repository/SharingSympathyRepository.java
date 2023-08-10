package com.umc.DongnaeFriend.domain.account.sharing.repository;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingSympathy;
import com.umc.DongnaeFriend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SharingSympathyRepository extends JpaRepository<SharingSympathy, Long> {
    @Query("SELECT u FROM User u WHERE u.id = :user_id")
    User findByUserId(@Param("user_id") Long user_id);
    @Query("SELECT sb FROM SharingBoard sb WHERE sb.id = :sharing_board_id")
    SharingBoard findBySharingBoardId(@Param("sharing_board_id") Long sharing_board_id);
    @Query(value = "SELECT sharing_sympathy.* FROM sharing_sympathy WHERE sharing_sympathy.sharing_board_id = :sharing_board_id", nativeQuery = true)
    SharingSympathy findBySharingBoardId(@Param("sharing_board_id") SharingBoard sharing_board_id);


    int countAllBySharingBoardId(Long sharing_board_id);
    int countAllByUserId(Long userId);
    List<SharingSympathy> findByUser_Id(long user_id);

    @Query(value = "select s from SharingSympathy s join fetch s.sharingBoard sb " +
            "where s.user.id = :userId order by s.createdAt desc")
    List<SharingSympathy> getSharingSympathyByUserId(@Param("userId") Long userId);
}
