package sms.capstone.authority.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
