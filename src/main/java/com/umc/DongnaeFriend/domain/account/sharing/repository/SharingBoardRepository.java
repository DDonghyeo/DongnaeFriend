package com.umc.DongnaeFriend.domain.account.sharing.repository;

import com.umc.DongnaeFriend.domain.account.sharing.dto.SharingDto;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingBoard;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface SharingBoardRepository extends JpaRepository<SharingBoard,Long> {


//    private final EntityManager em;
//
//    public SharingBoardRepository(EntityManager em) {
//        this.em = em;
//    }

//    public List<SharingDto.ListResponse> findByKeywordAAndCategory(@Param("keyword") String keyword, @Param("category") String category, Pageable pageable) {
//        return em.createQuery("SELECT new com.umc.DongnaeFriend.domain.account.sharing.dto.SharingDto.ListResponse(s.id, s.category, s.title, s.content, s.createdAt, s.view, likes), count(SharingSympathy.sharingBoard.id) as likes " +
//                        "FROM SharingBoard s left join SharingSympathy on s.id=SharingSympathy.sharingBoard.id " +
//                        "WHERE (s.title LIKE %:keyword% or s.content LIKE % :keyword%) and s.category = :category " +
//                        "group by s.id", SharingDto.ListResponse.class)
//                .setParameter("keyword", keyword)
//                .setParameter("category", category)
//                .getResultList();
//    }


    @Query(value = "SELECT sharing_board.*, COUNT(sharing_sympathy.sharing_board_id) AS cnt FROM sharing_board\n" +
            "LEFT JOIN sharing_sympathy ON  sharing_board.sharing_board_id = sharing_sympathy.sharing_board_id\n" +
            "WHERE (sharing_board.title LIKE %:keyword% OR sharing_board.content LIKE %:keyword%)\n" +
            "AND sharing_board.category = :category GROUP BY sharing_board.sharing_board_id ", nativeQuery = true)
    List<SharingBoard> findByKeywordOrderByLikes(@Param("keyword") String keyword, @Param("category") String category, Pageable pageable);




}
