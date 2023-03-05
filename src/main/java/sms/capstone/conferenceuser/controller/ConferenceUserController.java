package sms.capstone.conferenceuser.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import sms.capstone.conference.exception.NotProceedingConference;
import sms.capstone.conference.service.ConferenceService;
import sms.capstone.conferenceuser.dto.ConferenceId;
import sms.capstone.conferenceuser.service.ConferenceUserService;
import sms.capstone.global.util.SecurityUtil;
import sms.capstone.global.util.response.SingleResponse;
import sms.capstone.user.User;
import sms.capstone.user.repository.UserRepository;

@RequiredArgsConstructor
@RequestMapping("/api/conference")
@RestController
public class ConferenceUserController {
    private final ConferenceUserService conferenceUserService;
    private final UserRepository userRepository;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/enroll")
    public SingleResponse createEnrollment(@RequestBody ConferenceId conferenceId){
        conferenceUserService.enrollConference(conferenceId.getConferenceId());
        System.out.println("success");
        return new SingleResponse(true);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{conferenceId}/valid")
    public SingleResponse getEnrollment(@PathVariable("conferenceId") Long conferenceId){
        User user = SecurityUtil.getCurrentEmail().flatMap(userRepository::findOneByEmail).orElse(null);
        if (conferenceUserService.validationParticate(conferenceId, user)){
//            if (conferenceService.)
            return new SingleResponse(true);
        } else {
            throw (new NotProceedingConference("진행중인 컨퍼런스가 아닙니다."));
        }
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PatchMapping("/{conferenceId}/cancel")
    public SingleResponse deleteEnrollment(@PathVariable("conferenceId") Long conferenceId){
        conferenceUserService.cancelEnrollment(conferenceId);
        return new SingleResponse(true);
    }
}
