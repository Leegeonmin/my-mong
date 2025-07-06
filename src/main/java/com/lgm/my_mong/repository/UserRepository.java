package com.lgm.my_mong.repository;

import com.lgm.my_mong.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // username으로 사용자 찾기 (로그인용)
    Optional<User> findByUsername(String username);

    // username 중복 체크용
    boolean existsByUsername(String username);

    // nickname 중복 체크용
    boolean existsByNickname(String nickname);

    // 랭킹 조회용 (rating 내림차순)
    List<User> findAllByOrderByRatingDesc();
}
