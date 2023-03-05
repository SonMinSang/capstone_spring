package sms.capstone.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import sms.capstone.authority.Authority;
import sms.capstone.user.Gender;
import sms.capstone.user.User;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotNull
    @Email
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}",
            message = "비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상 포함된 6자~12자의 비밀번호여야 합니다.")
    private String password;

    @NotNull
    @Size(max = 10)
    private String nickname;

    @NotNull
    @Size(max = 5)
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Pattern(regexp="^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",
            message = "잘못된 형식입니다.")
    private String phoneNumber;


    private Set<Authority> authoritySet;

    public static UserDto from(User user) {
        if(user == null) return null;

        return UserDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .nickname(user.getNickname())
                .authoritySet(user.getAuthorities())
                .build();
    }
}