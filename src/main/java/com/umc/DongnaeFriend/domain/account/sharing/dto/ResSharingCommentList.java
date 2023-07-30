package com.umc.DongnaeFriend.domain.account.sharing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.umc.DongnaeFriend.domain.account.sharing.entity.SharingComment;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
public class ResSharingCommentList {
    int totalCount;
    List<SharingComment> commentList;

    @Builder
    public ResSharingCommentList(int totalCount, List<SharingComment> commentList) {
        this.totalCount = totalCount;
        this.commentList = commentList;
    }
}
