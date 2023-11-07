package com.gigacoffeebackend.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.socialLoginId = ?1 and u.nickName = ?2")
    User findBySocialLoginIdAndNickName(String socialLoginId, String nickname);

    @Query("select u from User u where u.id = ?1")
    Optional<User> findUserById(Long userId);
}
