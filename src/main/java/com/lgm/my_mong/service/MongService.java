package com.lgm.my_mong.service;

import com.lgm.my_mong.repository.MongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MongService {
    private final MongRepository mongRepository;
}
