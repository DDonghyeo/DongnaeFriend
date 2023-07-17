package com.umc.DongnaeFriend.domain.type;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionCategory {
    FOODS(0, "식비"),
    TRANSPORTATION(1, "교통비"),
    CULTURE(2, "문화"),
    DAILY_NECESSITY(3, "생필품"),
    SECOND_HAND(4, "중고거래"),
    FASHION(5, "미용/패션"),
    HEALTH(6, "건강"),
    EDUCATION(7, "교육"),
    FIXED_EXPENSES(8, "고정 지출"),
    OTT(9, "OTT"),
    ETC(10, "기타");


    private final Integer value;
    private final String category;

    @JsonValue
    public String geCategory() {
        return this.category;
    }
}
