package sms.capstone.global.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    private String accessToken;
    private String refreshToken;
}