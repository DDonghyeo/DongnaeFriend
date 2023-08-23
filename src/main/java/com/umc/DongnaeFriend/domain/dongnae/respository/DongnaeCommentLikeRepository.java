package com.umc.DongnaeFriend.domain.dongnae.respository;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingSympathy;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeComment;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeCommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DongnaeCommentLikeRepository extends JpaRepository<DongnaeCommentLike, Long> {
    @Query(value = "SELECT dongnae_comment_like.* FROM dongnae_comment_like WHERE dongnae_comment_like.dongnae_comment_id = :comment_id", nativeQuery = true)
    DongnaeCommentLike findByCommentId(@Param("comment_id") DongnaeComment comment_id);

    int countAllByDongnaeCommentId(Long dongnae_comment_id);
}
