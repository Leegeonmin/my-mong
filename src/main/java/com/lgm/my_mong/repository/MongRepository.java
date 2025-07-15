package com.lgm.my_mong.repository;

import com.lgm.my_mong.entity.Mong;
import com.lgm.my_mong.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongRepository extends JpaRepository<Mong, Long> {
    boolean existsByOwner(User user);
    boolean existsByName(String name);
}
