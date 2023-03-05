package sms.capstone.conference.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sms.capstone.conference.dto.ConferenceId;
import sms.capstone.conference.service.ConferenceAdminService;
import sms.capstone.global.util.response.SingleResponse;

@PreAuthorize("hasAnyRole('ADMIN')")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/conference")
public class ConferenceAdminController {
    private final ConferenceAdminService conferenceAdminService;

    @PatchMapping("/{conferenceId}/permit")
    public SingleResponse permitConference(@PathVariable("conferenceId") Long conferenceId){
        conferenceAdminService.permitConference(conferenceId);
        return new SingleResponse(true);
    }

    @PatchMapping("/{conferenceId}/deny")
    public SingleResponse denyConference(@PathVariable("conferenceId") Long conferenceId){
        conferenceAdminService.denyConference(conferenceId);
        return new SingleResponse(true);
    }

    @DeleteMapping("/{conferenceId}")
    public SingleResponse deleteConference(@PathVariable("conferenceId") Long conferenceId){
        conferenceAdminService.deleteConference(conferenceId);
        return new SingleResponse(true);
    }


}
