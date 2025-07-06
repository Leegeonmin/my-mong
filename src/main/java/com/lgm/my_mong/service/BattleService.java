package com.lgm.my_mong.service;

import com.lgm.my_mong.repository.BattleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BattleService {
    private final BattleRepository battleRepository;
}
