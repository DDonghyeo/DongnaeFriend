package com.umc.DongnaeFriend.global.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeUtil {

    //시간 계산
    public static String getTime(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now(); // 현재 시간
        Duration duration = Duration.between(now, time);

        long days = -duration.toDays();
        long hours = -duration.toHours() % 24;
        long minutes = -duration.toMinutes() % 60;

        if (days >= 1){
            if (days == 1) {
                return "어제";
            }
            return days + "일 전";
        }

        else if (hours >= 1) {
            return hours + "시간 전";
        } else return minutes + "분 전";

    }
}
