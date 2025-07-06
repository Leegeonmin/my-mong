package com.lgm.my_mong.repository;

import com.lgm.my_mong.entity.Mong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongRepository extends JpaRepository<Mong, Integer> {
}
