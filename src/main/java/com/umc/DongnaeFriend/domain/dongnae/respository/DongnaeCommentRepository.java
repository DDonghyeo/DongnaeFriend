package com.umc.DongnaeFriend.domain.dongnae.respository;

import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DongnaeCommentRepository extends JpaRepository<DongnaeComment, Long> {
    public int countAllByDongnaeBoardId(Long dongnae_board_id);
}
