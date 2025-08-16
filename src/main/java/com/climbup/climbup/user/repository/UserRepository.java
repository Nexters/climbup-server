package com.climbup.climbup.user.repository;

import com.climbup.climbup.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByKakaoId(String kakaoId);

    boolean existsByNickname(String nickname);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.attempts WHERE u.id = :userId")
    Optional<User> findByIdWithAttempts(@Param("userId") Long userId);
}
