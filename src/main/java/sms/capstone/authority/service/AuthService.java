package sms.capstone.authority.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sms.capstone.authority.exception.UnauthorizedException;
import sms.capstone.global.dto.TokenDto;
import sms.capstone.global.security.jwt.TokenProvider;
import sms.capstone.global.util.RedisUtil;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisUtil redisUtil;

    // 로그인 관련 메서드
    public TokenDto authorize(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String authorities = getAuthorities(authentication);

        return tokenProvider.createToken(authentication.getName(), authorities);
    }

    // 토큰 재발급 관련 메서드
    public TokenDto reissue(String requestAccessToken, String requestRefreshToken) {
        if (!tokenProvider.validateToken(requestRefreshToken)) {
            throw new UnauthorizedException("유효하지 않은 RefreshToken 입니다");
        }
        Authentication authentication = tokenProvider.getAuthentication(requestAccessToken);

        User principal = (User) authentication.getPrincipal();

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authorities = getAuthorities(authentication);

        return tokenProvider.createToken(principal.getUsername(), authorities);
    }

    // 권한 가져오기
    public String getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    public void logout(String accessToken, String refreshToken) {
        redisUtil.setBlackList(accessToken, "accessToken", 1800);
        redisUtil.setBlackList(refreshToken, "refreshToken", 60400);
    }
}