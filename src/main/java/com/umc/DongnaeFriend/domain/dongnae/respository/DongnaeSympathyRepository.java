package com.umc.DongnaeFriend.domain.dongnae.respository;

import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeSympathy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.web.PageableDefault;

import java.util.List;
import java.util.Optional;

public interface DongnaeSympathyRepository extends JpaRepository<DongnaeSympathy, Long> {
    int countAllByDongnaeBoardId(Long dongnae_board_id);

    List<DongnaeSympathy> findByUser_Id(long user_id);
}
