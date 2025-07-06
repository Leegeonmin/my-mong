package com.lgm.my_mong.security;


import com.lgm.my_mong.exception.CustomException;
import com.lgm.my_mong.exception.ResponseCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {


    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.expiration}")
    private long validityInMilliseconds;

    public String createToken(String username) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(validity)
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parser().verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        }catch (ExpiredJwtException e) {
            // 토큰 만료
            throw new CustomException(ResponseCode.VALIDATE_TOKEN_FAIL);
        } catch (SecurityException | MalformedJwtException e) {
            // 잘못된 JWT 서명
            throw new CustomException(ResponseCode.TOKEN_SIGNATURE_FAIL);
        } catch (UnsupportedJwtException e) {
            // 지원되지 않는 JWT 토큰
            throw new CustomException(ResponseCode.NOT_SUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            // JWT 토큰이 잘못되었습니다
            throw new CustomException(ResponseCode.ILLEGAL_TOKEN);
        }

    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();  // subject에서 username 추출
    }
}
