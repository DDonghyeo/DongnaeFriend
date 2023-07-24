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
        } catch (SignatureException ex) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        } catch (NullPointerException ex){
            log.error("JWT is empty");
        } catch (IllegalStateException e) {
            log.info("JWT is illegal");
        }
        return false;
    }
}
