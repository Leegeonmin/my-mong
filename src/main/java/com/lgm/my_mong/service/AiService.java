package com.lgm.my_mong.service;

import com.lgm.my_mong.dto.MongDTO;
import com.lgm.my_mong.entity.Mong;
import com.lgm.my_mong.entity.User;
import com.lgm.my_mong.exception.CustomException;
import com.lgm.my_mong.exception.ResponseCode;
import com.lgm.my_mong.provider.OpenAiProvider;
import com.lgm.my_mong.repository.MongRepository;
import com.lgm.my_mong.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AiService {

    private final OpenAiProvider openAiProvider;
    private final UserRepository userRepository;
    private final MongRepository mongRepository;

    @Transactional(readOnly = false)
    public MongDTO generateMonster(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));

        if(mongRepository.existsByOwner(user)) {
            throw new CustomException(ResponseCode.ALREADY_MONG_EXIST);
        }

        MongDTO response = openAiProvider.generateMonster();

        Mong newMong = mongRepository.save(Mong.builder()
                .name(response.getName())
                .owner(user)
                .title(response.getTitle())
                .description(response.getDescription())
                .build());

        return response;
    }

//    public String judgeBattle(String challenger, String opponent) {
//         response = openAiProvider.battleMonsters(challenger, opponent);
//        return response.getChoices().get(0).getMessage().getContent();
//    }

}