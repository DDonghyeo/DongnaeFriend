package com.umc.DongnaeFriend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserDto {

    @Getter
    @AllArgsConstructor
    public static class Request {

        String accessToken;

        String type;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {

        String accessToken;

        String refreshToken;

    }

    @Getter
    @AllArgsConstructor
    public static class SignUpDto {

        String nickName;

        String email;

        Long kakaoId;

    }

}
