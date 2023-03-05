package sms.capstone.conferenceuser.exception;

public class DuplicateApplyConference extends RuntimeException {
    public DuplicateApplyConference() {
        super();
    }
    public DuplicateApplyConference(String message, Throwable cause) {
        super(message, cause);
    }
    public DuplicateApplyConference(String message) {
        super(message);
    }
    public DuplicateApplyConference(Throwable cause) {
        super(cause);
    }
}
