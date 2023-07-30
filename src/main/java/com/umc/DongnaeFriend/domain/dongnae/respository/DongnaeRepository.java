package com.umc.DongnaeFriend.domain.dongnae.respository;

import com.umc.DongnaeFriend.domain.dongnae.entity.Dongnae;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DongnaeRepository extends JpaRepository<Dongnae, Long> {

}
