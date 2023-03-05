package sms.capstone.user.service;

import java.util.Collections;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sms.capstone.authority.Authority;
import sms.capstone.user.dto.UserDto;
import sms.capstone.user.dto.UserEditDto;
import sms.capstone.user.dto.UserSignUpDto;
import sms.capstone.user.exception.DuplicateMemberException;
import sms.capstone.global.util.SecurityUtil;
import sms.capstone.authority.repository.AuthorityRepository;
import sms.capstone.user.User;
import sms.capstone.user.repository.UserRepository;
import sms.capstone.global.util.response.SingleResponse;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserSignUpDto addUser(UserSignUpDto userSignUpDto) {
        if (userRepository.findOneByEmail(userSignUpDto.getEmail()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        authorityRepository.save(authority);

        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .password(passwordEncoder.encode(userSignUpDto.getPassword()))
                .gender(userSignUpDto.getGender())
                .name(userSignUpDto.getName())
                .phoneNumber(userSignUpDto.getPhoneNumber())
                .nickname(userSignUpDto.getNickname())
                .authorities(Collections.singleton(authority))
                .build();

        return UserSignUpDto.from(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public SingleResponse getMyUserWithAuthorities() {
        return new SingleResponse(
                UserDto.from(SecurityUtil.getCurrentEmail().
                        flatMap(userRepository::findOneWithAuthoritiesByEmail).
                        orElse(null)));
    }

    public UserEditDto updateUserInfo(UserEditDto userEditDto) {
        User authUser = SecurityUtil.getCurrentEmail().flatMap(userRepository::findOneByEmail).orElse(null);
        return userRepository.findById(authUser.getId()).get().update(userEditDto);
    }

    public void deleteMyAccount() {
        User user = SecurityUtil.getCurrentEmail().flatMap(userRepository::findOneByEmail).orElse(null);
        userRepository.delete(user);
    }

    public void delete(Long userId) {
        User user = userRepository.getById(userId);
    }
}
