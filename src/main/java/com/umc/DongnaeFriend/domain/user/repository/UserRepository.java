package com.umc.DongnaeFriend.domain.user.repository;

import com.umc.DongnaeFriend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);

    Optional<User> findByRefreshToken(String refresh_token);

    Optional<User> findByKakaoId(Long id);
}
