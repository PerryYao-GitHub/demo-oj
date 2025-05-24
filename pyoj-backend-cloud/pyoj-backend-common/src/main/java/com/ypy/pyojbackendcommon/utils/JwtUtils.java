package com.ypy.pyojbackendcommon.utils;

import com.ypy.pyojbackendcommon.app.AppCode;
import com.ypy.pyojbackendcommon.exception.AppException;
import com.ypy.pyojbackendcommon.model.dto.UserAuthDTO;
import io.jsonwebtoken.*;
import java.util.Date;

public class JwtUtils {

    // 你应将 SECRET_KEY 保存在配置文件中，并通过配置注入
    private static final String SECRET_KEY = "hasndnaGThal3JKncc2abskFFgoiqu7";
    private static final long EXPIRATION_MS = 86400000L; // 1天

    /**
     * 生成 JWT token
     */
    public static String generateToken(UserAuthDTO user) {
        return Jwts.builder()
                .claim("id", user.getId())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * 解析 token 获取 UserAuthDTO
     */
    public static UserAuthDTO parseToken(String token) throws AppException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            Long id = claims.get("id", Long.class);
            Integer roleInt = claims.get("role", Integer.class); // 先转成 Integer，再转为 Byte
            Byte role = roleInt != null ? roleInt.byteValue() : null;

            return new UserAuthDTO(id, role);
        } catch (ExpiredJwtException e) {
            throw new AppException(AppCode.ERR_TOKEN_EXPIRE);
        } catch (JwtException e) {
            throw new AppException(AppCode.ERR_TOKEN);
        }
    }
}
