package sms.capstone.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sms.capstone.user.dto.UserDto;
import sms.capstone.global.util.response.ListResponse;
import sms.capstone.global.util.response.SingleResponse;
import sms.capstone.user.User;
import sms.capstone.user.service.UserService;
import sms.capstone.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/user")
@PreAuthorize("hasAnyRole('ADMIN')")
public class UserAdminController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping
    public ListResponse getAllUserInfo(HttpServletRequest request) {
        List<User> users = userRepository.findAll();
        List<UserDto> result = users.stream()
                .map(UserDto::from)
                .collect(toList());
        return new ListResponse(result);
    }
    @GetMapping("/{userId}")
    public SingleResponse getUserInfo(@PathVariable Long userId) {
        return new SingleResponse(UserDto.from(userRepository.getById(userId)));
    }

    @DeleteMapping("/{userId}")
    public SingleResponse deleteUserAccount(@PathVariable Long userId) {
        userService.delete(userId);
        return new SingleResponse(true);
    }


}
