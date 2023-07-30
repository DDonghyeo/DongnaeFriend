package com.umc.DongnaeFriend.domain.account.sharing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReqSharingCommentDto {
    Long parentCommentId;
    String content;
}
