package sms.capstone.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sms.capstone.conference.exception.ExceedLimitMemberException;
import sms.capstone.conferenceuser.exception.DuplicateApplyConference;
import sms.capstone.user.exception.DuplicateMemberException;
import sms.capstone.global.util.response.ErrorResponse;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(DuplicateMemberException.class)
    private ResponseEntity<ErrorResponse> duplicateMemberException(DuplicateMemberException e){
        log.info(e.getMessage());
        return ResponseEntity.status(409).body(new ErrorResponse(409, e.getMessage()));
    }

    @ExceptionHandler(ExceedLimitMemberException.class)
    private ResponseEntity<ErrorResponse> exceedLimitMemberException(ExceedLimitMemberException e){
        log.info(e.getMessage());
        return ResponseEntity.status(409).body(new ErrorResponse(409, e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    private ResponseEntity<ErrorResponse> authenticationException(AuthenticationException e){
        log.info(e.getMessage());
        return ResponseEntity.status(401).body(new ErrorResponse(401, e.getMessage()));
    }
    @ExceptionHandler(DuplicateApplyConference.class)
    private ResponseEntity<ErrorResponse> DuplicateApplyConference(DuplicateApplyConference e){
        log.info(e.getMessage());
        return ResponseEntity.status(409).body(new ErrorResponse(409, e.getMessage()));
    }

}
