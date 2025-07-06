package com.lgm.my_mong.service;

import com.lgm.my_mong.dto.AuthDTO;
import com.lgm.my_mong.entity.User;
import com.lgm.my_mong.exception.CustomException;
import com.lgm.my_mong.exception.ResponseCode;
import com.lgm.my_mong.repository.UserRepository;
import com.lgm.my_mong.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    /**
     * 회원가입
     */
    @Transactional
    public AuthDTO.TokenResponse signUp(AuthDTO.SignUpRequest request) {

        // 중복 체크
        validateDuplicateUser(request.getUsername(), request.getNickname());

        // 사용자 생성
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .rating(1000) // 기본 랭킹 점수
                .build();

        User savedUser = userRepository.save(user);

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.createToken(savedUser.getUsername());

        return new AuthDTO.TokenResponse(
                accessToken,
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getNickname(),
                savedUser.getRating()
        );
    }

    /**
     * 중복 체크 (username, nickname)
     */
    private void validateDuplicateUser(String username, String nickname) {
        if (userRepository.existsByUsername(username)) {
            throw new CustomException(ResponseCode.DUPLICATE_USERNAME);
        }

        if (userRepository.existsByNickname(nickname)) {
            throw new CustomException(ResponseCode.DUPLICATE_NICKNAME);
        }
    }

    /**
     * 로그인
     */
    @Transactional(readOnly = true)
    public AuthDTO.TokenResponse signIn(AuthDTO.SignInRequest request) {

        // 사용자 조회
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ResponseCode.INVALID_PASSWORD);
        }

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.createToken(user.getUsername());


        return new AuthDTO.TokenResponse(
                accessToken,
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getRating()
        );
    }
    /**
     * 현재 로그인한 사용자 정보 조회
     */
    @Transactional(readOnly = true)
    public AuthDTO.UserInfo getCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ResponseCode.USER_NOT_FOUND));

        return new AuthDTO.UserInfo(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getRating()
        );
    }

}
