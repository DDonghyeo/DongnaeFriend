package com.umc.DongnaeFriend.domain.account.sharing.repository;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingComment;
import com.umc.DongnaeFriend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;

public interface SharingCommentRepository extends JpaRepository<SharingComment, Long> {
    @Query("SELECT u FROM User u WHERE u.id = :user_id")
    User findByUserId(@Param("user_id") Long user_id);
    @Query("SELECT sb FROM SharingBoard sb WHERE sb.id = :sharing_board_id")
    SharingBoard findBySharingBoardId(@Param("sharing_board_id") Long sharing_board_id);

    @Query("SELECT sc FROM SharingComment sc WHERE sc.sharingBoard = :sharingBoard")
    List<SharingComment> findAllByBoard(@Param("sharingBoard") SharingBoard sharingBoard);
}
