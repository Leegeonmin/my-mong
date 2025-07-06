package com.lgm.my_mong.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpRequest {

        @NotBlank(message = "사용자명은 필수입니다")
        @Size(min = 4, max = 20, message = "사용자명은 4-20자여야 합니다")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "사용자명은 영문, 숫자, 언더스코어만 가능합니다")
        private String username;

        @NotBlank(message = "비밀번호는 필수입니다")
        @Size(min = 6, max = 20, message = "비밀번호는 6-20자여야 합니다")
        private String password;

        @NotBlank(message = "닉네임은 필수입니다")
        @Size(min = 2, max = 10, message = "닉네임은 2-10자여야 합니다")
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignInRequest {

        @NotBlank(message = "사용자명은 필수입니다")
        private String username;

        @NotBlank(message = "비밀번호는 필수입니다")
        private String password;
    }

    @Getter
    public static class TokenResponse {
        private String accessToken;
        private Long userId;
        private String username;
        private String nickname;
        private Integer rating;

        public TokenResponse(String accessToken, Long userId, String username, String nickname, Integer rating) {
            this.accessToken = accessToken;
            this.userId = userId;
            this.username = username;
            this.nickname = nickname;
            this.rating = rating;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class UserInfo {
        private Long id;
        private String username;
        private String nickname;
        private Integer rating;
    }
}