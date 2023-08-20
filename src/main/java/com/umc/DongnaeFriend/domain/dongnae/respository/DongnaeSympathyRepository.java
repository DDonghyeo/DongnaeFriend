package com.umc.DongnaeFriend.domain.dongnae.respository;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingSympathy;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeSympathy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

import java.util.List;
import java.util.Optional;

public interface DongnaeSympathyRepository extends JpaRepository<DongnaeSympathy, Long> {
    int countAllByDongnaeBoardId(Long dongnae_board_id);
    int countAllByUserId(Long userId);

    List<DongnaeSympathy> findByUser_Id(long user_id);
    @Query(value = "SELECT dongnae_sympathy.* FROM dongnae_sympathy WHERE dongnae_sympathy.dongnae_board_id = :dongnae_board_id", nativeQuery = true)
    DongnaeSympathy findByDongnaeBoardId(@Param("dongnae_board_id") DongnaeBoard dongnae_board_id);

    @Query(value = "SELECT dongnae_sympathy.* FROM dongnae_sympathy WHERE dongnae_sympathy.user_id = :user_id", nativeQuery = true)
    List<DongnaeSympathy> findAllByUserId(@Param("user_id") Long user_id);
}
