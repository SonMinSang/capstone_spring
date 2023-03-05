package sms.capstone.conference.exception;

public class NotConferenceMemberException extends RuntimeException{
    public NotConferenceMemberException() {
        super();
    }
    public NotConferenceMemberException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotConferenceMemberException(String message) {
        super(message);
    }
    public NotConferenceMemberException(Throwable cause) {
        super(cause);
    }
}
