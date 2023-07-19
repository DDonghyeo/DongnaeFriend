package com.umc.DongnaeFriend.domain.dongnae.respository;

import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeSympathy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DongnaeSympathyRepository extends JpaRepository<DongnaeSympathy, Long> {
    public int countAllByDongnaeBoardId(Long dongnae_board_id);
}
