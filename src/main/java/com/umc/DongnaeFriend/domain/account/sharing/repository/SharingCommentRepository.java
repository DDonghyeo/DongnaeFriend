package com.umc.DongnaeFriend.domain.account.sharing.repository;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingComment;
import com.umc.DongnaeFriend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface SharingCommentRepository extends JpaRepository<SharingComment, Long> {
    @Query("SELECT u FROM User u WHERE u.id = :user_id")
    User findByUserId(@Param("user_id") Long user_id);
    @Query("SELECT sb FROM SharingBoard sb WHERE sb.id = :sharing_board_id")
    SharingBoard findBySharingBoardId(@Param("sharing_board_id") Long sharing_board_id);
    @Query(value = "SELECT sharing_comment.*, COUNT(sharing_comment_like.sharing_comment_id) AS cnt FROM sharing_comment\n" +
            "LEFT JOIN sharing_comment_like ON sharing_comment.sharing_comment_id = sharing_comment_like.sharing_comment_id\n" +
            "WHERE sharing_comment.sharing_board_id = :sharingBoard\n" +
            "GROUP BY sharing_comment.sharing_comment_id ORDER BY cnt DESC;", nativeQuery = true)
    List<SharingComment> findAllByBoard(@Param("sharingBoard") SharingBoard sharingBoard);

    public int countAllBySharingBoardId(Long sharing_board_id);
    int countAllByUserId(Long userId);

    @Query(value = "select c from SharingComment c join fetch c.sharingBoard sb " +
            "where c.user.id = :userId order by c.createdAt desc")
    List<SharingComment> getCommentByUserIdAndBoard(@Param("userId") Long userId);

}
