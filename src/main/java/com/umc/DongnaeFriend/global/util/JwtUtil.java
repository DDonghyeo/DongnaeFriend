package com.umc.DongnaeFriend.global.util;

import com.umc.DongnaeFriend.global.exception.CustomException;
import com.umc.DongnaeFriend.global.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.*;

import static com.umc.DongnaeFriend.config.JwtConfig.SECRET_KEY;

public class JwtUtil {

    public static Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.get("userId").toString());
    }

    //token 유효성 검증
    public static Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException | IllegalArgumentException | MalformedJwtException | ExpiredJwtException |
                 UnsupportedJwtException | NullPointerException | IllegalStateException ex) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }
    }
}
