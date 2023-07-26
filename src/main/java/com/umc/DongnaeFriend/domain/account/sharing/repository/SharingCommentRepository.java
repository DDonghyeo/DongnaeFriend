package com.umc.DongnaeFriend.domain.account.sharing.repository;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingComment;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SharingCommentRepository extends JpaRepository<SharingComment, Long> {

    public int countAllBySharingBoardId(Long sharing_board_id);
    int countAllByUserId(Long userId);

    @Query(value = "select c from SharingComment c join fetch c.sharingBoard sb " +
            "where c.user.id = :userId order by c.createdAt desc")
    List<SharingComment> getCommentByUserIdAndBoard(@Param("userId") Long userId);
}
