package sms.capstone.authority.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sms.capstone.authority.dto.LoginDto;
import sms.capstone.authority.service.AuthService;
import sms.capstone.global.dto.TokenDto;
import sms.capstone.global.security.jwt.JwtFilter;
import sms.capstone.global.security.jwt.TokenProvider;
import sms.capstone.global.util.response.SingleResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(
            @RequestBody @Valid LoginDto loginDto) {
        TokenDto tokenDto = authService.authorize(loginDto.getEmail(), loginDto.getPassword());
        return ResponseEntity.ok(tokenDto);
    }

    /*
     * AccessToken이 만료되었을 때 토큰(AccessToken , RefreshToken)재발급해주는 메서드
     */
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(
            @RequestBody @Valid TokenDto requestTokenDto) {
        TokenDto tokenDto = authService
                .reissue(requestTokenDto.getAccessToken(), requestTokenDto.getRefreshToken());
        return ResponseEntity.ok(tokenDto);
    }

    @DeleteMapping("/logout")
    public SingleResponse logout(@RequestBody @Valid TokenDto requestTokenDto) {


        authService.logout(requestTokenDto.getAccessToken(), requestTokenDto.getRefreshToken());

        return new SingleResponse<>(true);
    }
}