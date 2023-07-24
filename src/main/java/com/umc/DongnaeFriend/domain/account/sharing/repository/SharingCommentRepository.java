package com.umc.DongnaeFriend.domain.account.sharing.repository;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharingCommentRepository extends JpaRepository<SharingComment, Long> {

    public int countAllBySharingBoardId(Long sharing_board_id);
}
