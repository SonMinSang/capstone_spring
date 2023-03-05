package sms.capstone.conference.exception;

public class ExceedLimitMemberException extends RuntimeException{
    public ExceedLimitMemberException() { super(); }
    public ExceedLimitMemberException(String message, Throwable cause) {
        super(message, cause);
    }
    public ExceedLimitMemberException(String message) {
        super(message);
    }
    public ExceedLimitMemberException(Throwable cause) {
        super(cause);
    }
}
