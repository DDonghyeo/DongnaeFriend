package com.umc.DongnaeFriend.domain.dongnae.respository;

import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DongnaeImgRepository extends JpaRepository<DongnaeImg,Long> {

    @Query(value = "SELECT * FROM dongnae_img WHERE dongnae_board_id = ?1 ORDER BY created_at ASC LIMIT 1", nativeQuery = true)
    Optional<DongnaeImg> findFirst(long dongnae_board_id);

    List<DongnaeImg> findAllByDongnaeBoard_Id(long id);

    void deleteAllByDongnaeBoard_Id(Long board_id);
}
