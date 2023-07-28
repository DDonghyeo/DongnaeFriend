package com.umc.DongnaeFriend.domain.type;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE(0, "남성"),
    FEMALE(1, "여성");

    private final Integer value;
    private final String gender;

    @JsonValue
    public String getGender() {
        return this.gender;
    }

    public static Gender fromString(String strGender) {

        for(Gender gender : Gender.values()){
            if(gender.getGender().equals(strGender)){
                return gender;
            }
        }
        throw new IllegalArgumentException("No matching type for [" + strGender + "]");
    }
}
