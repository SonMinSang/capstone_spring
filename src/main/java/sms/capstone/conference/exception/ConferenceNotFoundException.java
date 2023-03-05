package sms.capstone.conference.exception;

public class ConferenceNotFoundException extends RuntimeException{
    public ConferenceNotFoundException() {
        super();
    }
    public ConferenceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public ConferenceNotFoundException(String message) {
        super(message);
    }
    public ConferenceNotFoundException(Throwable cause) {
        super(cause);
    }
}
