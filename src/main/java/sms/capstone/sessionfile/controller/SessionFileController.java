package sms.capstone.sessionfile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sms.capstone.conference.service.ConferenceService;
import sms.capstone.global.util.response.SingleResponse;
import sms.capstone.sessionfile.service.S3Service;
import sms.capstone.sessionfile.service.SessionFileService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SessionFileController {
    private final SessionFileService sessionFileService;
    private final S3Service s3Service;
    private final ConferenceService conferenceService;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{sessionId}/files")
    public SingleResponse getSessionFiles(Long sessionId) {
        conferenceService.validConferenceMember(sessionId);
        sessionFileService.getSessionFiles(sessionId);
        return new SingleResponse(true);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/{sessionId}/files")
    public SingleResponse uploadSessionFiles(Long sessionId, List<MultipartFile> multipartFile) throws IOException {
        conferenceService.validConferenceHost(sessionId);
        sessionFileService.uploadSessionFiles(sessionId, s3Service.upload(sessionId, multipartFile));
        return new SingleResponse(true);
    }
}
