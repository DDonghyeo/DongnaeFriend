package com.umc.DongnaeFriend.domain.type;

import com.umc.DongnaeFriend.global.exception.CustomException;
import com.umc.DongnaeFriend.global.exception.ErrorCode;
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

    public static SharingCategory valueOf(Integer value) {
        for (SharingCategory category : SharingCategory.values()) {
            if (category.getValue().equals(value)) {
                return category;
            } else throw new CustomException(ErrorCode.INVALID_VALUE);
        }
        throw new IllegalArgumentException("Invalid Category: " + value);
    }
}
