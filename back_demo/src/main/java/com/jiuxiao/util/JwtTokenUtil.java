package com.jiuxiao.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 悟道九霄
 * @Date 2023/10/8 18:19
 * @Description JWT令牌工具类
 */
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";

    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * @param userDetails 用户信息对象
     * @return: java.lang.String
     * @decription 根据用户信息生成Token
     * @date 2023/10/9 9:58
     */
    public String generateToken(UserDetails userDetails) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * @param token token令牌
     * @return: java.lang.String
     * @decription 从 token 中获取用户名
     * @date 2023/10/9 10:21
     */
    public String getUserNameFormToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFormToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * @param token       Token令牌
     * @param userDetails 用户信息对象
     * @return: boolean
     * @decription 校验 Token 是否有效
     * @date 2023/10/9 14:50
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFormToken(token);
        return username.equals(userDetails.getUsername()) && !tokenExpired(token);
    }

    /**
     * @param token Token令牌
     * @return: boolean
     * @decription 判断 Token 是否可以被刷新
     * @date 2023/10/9 14:56
     */
    public boolean canRefreshToken(String token) {
        return !tokenExpired(token);
    }

    /**
     * @param token Token 令牌
     * @return: java.lang.String
     * @decription 刷新 Token
     * @date 2023/10/9 14:57
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFormToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * @param token Token令牌
     * @return: boolean
     * @decription 验证 Token 是否过期
     * @date 2023/10/9 14:52
     */
    private boolean tokenExpired(String token) {
        Date expireDate = getExpiredDateFromToken(token);
        return expireDate.before(new Date());
    }

    /**
     * @param token Token令牌
     * @return: java.util.Date
     * @decription 根据 Token 令牌获得失效时间
     * @date 2023/10/9 14:54
     */
    private Date getExpiredDateFromToken(String token) {
        return getClaimsFormToken(token).getExpiration();
    }

    /**
     * @param token Token令牌
     * @return: io.jsonwebtoken.Claims
     * @decription 从 Token 中获取荷载信息
     * @date 2023/10/9 10:23
     */
    private Claims getClaimsFormToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * @param claims 令牌荷载
     * @return: java.lang.String
     * @decription
     * @date 2023/10/9 10:02
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * @return: java.util.Date
     * @decription 生成 jwt 令牌失效时间
     * @date 2023/10/9 10:08
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * @param data 要加密的字符串
     * @return: java.lang.String
     * @decription 使用 BCryptPasswordEncoder 对字符串进行加密
     * @date 2023/10/9 16:42
     */
    public static String encodeStringByBCrypt(String data) {
        return new BCryptPasswordEncoder().encode(data);
    }
}
