package sms.capstone.conference.exception;


public class NotConferenceHostException extends RuntimeException{
    public NotConferenceHostException() {
        super();
    }
    public NotConferenceHostException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotConferenceHostException(String message) {
        super(message);
    }
    public NotConferenceHostException(Throwable cause) {
        super(cause);
    }
}
