package com.umc.DongnaeFriend.domain.dongnae.respository;

import com.umc.DongnaeFriend.domain.dongnae.dto.DongnaeCommentDto;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeComment;
import com.umc.DongnaeFriend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DongnaeCommentRepository extends JpaRepository<DongnaeComment, Long> {
    public int countAllByDongnaeBoardId(Long dongnae_board_id);
    public int countAllByUserId(Long userId);

    @Query(value = "select c from DongnaeComment c join fetch c.dongnaeBoard d " +
            "where c.user.id = :userId order by c.createdAt desc")
            List<DongnaeComment> getCommentByUserIdAndBoard(@Param("userId") Long userId);
    @Query("SELECT u FROM User u WHERE u.id = :user_id")
    User findByUserId(@Param("user_id") Long user_id);

    @Query("SELECT db FROM DongnaeBoard db WHERE db.id = :dongnae_board_id")
    DongnaeBoard findByDongnaeBoardId(@Param("dongnae_board_id") Long dongnae_board_id);

    @Query(value = "SELECT dongnae_comment.* FROM dongnae_comment WHERE dongnae_comment.dongnae_board_id = :dongnae_board_id", nativeQuery = true)
    List<DongnaeComment> findListByBoardId(@Param("dongnae_board_id") DongnaeBoard dongnae_board_id);

}
