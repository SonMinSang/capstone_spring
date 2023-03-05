package sms.capstone.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sms.capstone.conference.Conference;
import sms.capstone.conferenceuser.ConferenceUser;
import sms.capstone.conference.dto.ConferenceElement;
import sms.capstone.user.dto.UserEditDto;
import sms.capstone.user.dto.UserSignUpDto;
import sms.capstone.conference.repository.ConferenceRepository;
import sms.capstone.global.util.response.ListResponse;
import sms.capstone.global.util.response.SingleResponse;
import sms.capstone.global.util.SecurityUtil;
import sms.capstone.user.User;
import sms.capstone.user.service.UserService;
import sms.capstone.user.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ConferenceRepository conferenceRepository;

    @PostMapping("/signup")
    public SingleResponse createUser(
            @Valid @RequestBody UserSignUpDto userSignUpDto
    ) {
        return new SingleResponse(userService.addUser(userSignUpDto));
    }

    @GetMapping("/user/details")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public SingleResponse getUser() {
        return new SingleResponse(userService.getMyUserWithAuthorities());
    }

    @PatchMapping("/user/details")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public SingleResponse updateUser(@Valid @RequestBody UserEditDto userEditDto){
        return new SingleResponse(userService.updateUserInfo(userEditDto));
    }

    @DeleteMapping("/user/details")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public SingleResponse deleteUser(){
        userService.deleteMyAccount();
        return new SingleResponse(true);
    }

    @GetMapping("/user/details/conferences")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ListResponse getAllRegisteredConferences() {

        User user = SecurityUtil.getCurrentEmail().flatMap(userRepository::findOneByEmail).orElse(null);
        List<ConferenceUser> conferenceUsers = conferenceRepository.findAllRegisteredConferences(user.getId());
        List<ConferenceElement> result = conferenceUsers.stream()
                .map(c -> new ConferenceElement(c.getConference()))
                .collect(toList());
        return new ListResponse(result);
    }

    @GetMapping("/user/details/host-conferences")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ListResponse getAllHostConferences() {

        User user = SecurityUtil.getCurrentEmail().flatMap(userRepository::findOneByEmail).orElse(null);
        List<Conference> conference = conferenceRepository.findAllHostConference(user.getId());
        List<ConferenceElement> result = conference.stream()
                .map(c -> new ConferenceElement(c))
                .collect(toList());
        return new ListResponse(result);
    }
}
