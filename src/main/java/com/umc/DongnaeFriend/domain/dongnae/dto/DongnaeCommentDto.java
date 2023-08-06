package com.umc.DongnaeFriend.domain.dongnae.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DongnaeCommentDto {
    Long parentCommentId;
    String content;
}
