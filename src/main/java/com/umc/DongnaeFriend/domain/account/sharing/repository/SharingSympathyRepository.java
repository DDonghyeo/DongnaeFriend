package com.umc.DongnaeFriend.domain.account.sharing.repository;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingSympathy;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeSympathy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SharingSympathyRepository extends JpaRepository<SharingSympathy, Long> {

    int countAllBySharingBoardId(Long sharing_board_id);

    List<SharingSympathy> findByUser_Id(long user_id);
}
