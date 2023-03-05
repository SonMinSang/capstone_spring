package sms.capstone.conference.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sms.capstone.conference.Conference;
import sms.capstone.conference.dto.*;
import sms.capstone.conference.repository.ConferenceRepository;
import sms.capstone.conference.service.ConferenceService;
import sms.capstone.user.repository.UserRepository;
import sms.capstone.global.util.response.ListResponse;
import sms.capstone.global.util.response.SingleResponse;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/conference")
public class ConferenceController {

    private final ConferenceService conferenceService;
    private final ConferenceRepository conferenceRepository;

    @GetMapping
    public ListResponse getAllConferences() {

        List<Conference> conferences = conferenceRepository.findAllWithSession();
        List<ConferenceElement> result = conferences.stream()
                .map(ConferenceElement::new)
                .collect(toList());

        return new ListResponse(conferenceService.getAllConferences());
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public SingleResponse createConference(@Valid @RequestBody ConferenceCreate conferenceCreate){

        return new SingleResponse(conferenceService.registerConference(conferenceCreate));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping
    public SingleResponse updateConference(@Valid @RequestBody ConferenceEdit conferenceEdit){

        conferenceService.editConference(conferenceEdit);
        return new SingleResponse(true);
    }

    @GetMapping("/{conferenceId}")
    public SingleResponse getConference(@PathVariable("conferenceId") Long conferenceId) {
        Conference conference = conferenceRepository.findById(conferenceId);
        ConferenceElement result = new ConferenceElement(conference);
        return new SingleResponse(result);
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{conferenceId}/server")
    public SingleResponse getServerUUID(@PathVariable("conferenceId") Long conferenceId){
        Conference conference = conferenceRepository.findById(conferenceId);
        return new SingleResponse(new ConferenceServerIdDto(conference));
    }



}
