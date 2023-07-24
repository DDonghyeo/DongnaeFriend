package com.umc.DongnaeFriend.domain.account.sharing.repository;

import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingImg;
import com.umc.DongnaeFriend.domain.dongnae.entity.DongnaeImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SharingImgRepository extends JpaRepository<SharingImg, Long> {

    @Query(value = "SELECT * FROM sharing_img WHERE sharing_board_id = ?1 ORDER BY created_at ASC LIMIT 1", nativeQuery = true)
    Optional<SharingImg> findFirst(long sharing_board_id);

    List<SharingImg> findAllBySharingBoard_Id(long id);

    void deleteAllById(long id);
}
