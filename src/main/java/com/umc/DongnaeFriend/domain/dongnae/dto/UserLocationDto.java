package com.umc.DongnaeFriend.domain.dongnae.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class UserLocationDto {
    @NotNull(message = "동네 정보가 없습니다.")
    private String town;
}
