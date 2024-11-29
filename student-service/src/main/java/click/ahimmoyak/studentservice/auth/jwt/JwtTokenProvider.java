package click.ahimmoyak.studentservice.auth.jwt;

import click.ahimmoyak.studentservice.auth.config.security.UserDetailsServiceImpl;
import click.ahimmoyak.studentservice.auth.common.UserRole;
import click.ahimmoyak.studentservice.auth.dto.JwsDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final UserDetailsServiceImpl userDetailsService;
    public static final String ACCESS_TOKEN = "Authorization";
    public static final String REFRESH_TOKEN = "Refresh";
    private static final String BEARER_PREFIX = "[Bearer]";
    private static final long ACCESS_TIME = 60 * 60 * 1000L;
    private static final long REFRESH_TIME = 7 * 24 * 60 * 60 * 1000L;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private SecretKey key;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }
    public String getTokenFromHeader(HttpServletRequest request, String jwsType) {
        String token = jwsType.equals(ACCESS_TOKEN) ? ACCESS_TOKEN : REFRESH_TOKEN;
        String bearerToken = request.getHeader(token);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(8);
        }

        return null;
    }

    public String createAccessToken(String username, String email, UserRole role) {
        Date date = new Date();
        long time = ACCESS_TIME;
        return BEARER_PREFIX + Jwts.builder()
                .subject(username)
                .claim("email", email)
                .claim("role", role)
                .issuer("Ahimmoyak")
                .issuedAt(date)
                .expiration(new Date(date.getTime() + time))
                .signWith(key)
                .compact();
    }

    public String createRefreshToken(String username) {
        Date date = new Date();
        long time = REFRESH_TIME;
        return BEARER_PREFIX + Jwts.builder()
                .subject(username)
                .issuer("Ahimmoyak")
                .issuedAt(date)
                .expiration(new Date(date.getTime() + time))
                .signWith(key)
                .compact();
    }



    public JwsDto createAllToken(String userId, String email, UserRole role) {
        return JwsDto.builder()
                .accessToken(createAccessToken(userId, email, role))
                .refreshToken(createRefreshToken(userId))
                .build();
    }

    public boolean validateToken(String jws) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(jws);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty");
        }
        return false;
    }

    public Authentication getAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String getUserInfoFromToken(String jws) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(jws).getPayload().getSubject();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token but Get username");
            return e.getClaims().getSubject();
        }
    }

    public void setHeaderAccessToken(HttpServletResponse response, String newAccessToken) {
        response.setHeader(ACCESS_TOKEN, newAccessToken);
    }
}