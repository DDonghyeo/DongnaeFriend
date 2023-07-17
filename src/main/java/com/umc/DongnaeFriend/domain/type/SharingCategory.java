package com.umc.DongnaeFriend.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SharingCategory {
    WORRIES(0, "고민"),
    BUY_OR_NOT(1, "살까 말까"),
    TIPS(2, "꿀팁"),
    ETC(3, "기타");

    private final Integer value;
    private final String category;
}
