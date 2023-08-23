package com.umc.DongnaeFriend.domain.account.sharing.repository;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingComment;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingCommentLike;
import com.umc.DongnaeFriend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SharingCommentLikeRepository extends JpaRepository<SharingCommentLike, Long> {
    @Query("SELECT u FROM User u WHERE u.id = :user_id")
    User findByUserId(@Param("user_id") Long user_id);

    @Query("SELECT sc FROM SharingComment sc WHERE sc.id = :sharing_comment_id")
    SharingComment findByCommentId(@Param("sharing_comment_id") Long sharing_comment_id);

    @Query(value = "SELECT sharing_comment_like.* FROM sharing_comment_like WHERE sharing_comment_like.sharing_comment_id = :comment_id", nativeQuery = true)
    SharingCommentLike findByCommentId(@Param("comment_id") SharingComment comment_id);

    int countAllBySharingCommentId(Long sharing_comment_id);
}
