package com.umc.DongnaeFriend.domain.dongnae.respository;

import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeComment;
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
}
