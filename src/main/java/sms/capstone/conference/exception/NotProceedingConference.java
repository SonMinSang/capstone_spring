package sms.capstone.conference.exception;


public class NotProceedingConference extends RuntimeException {
    public NotProceedingConference() {
            super();
        }
    public NotProceedingConference(String message, Throwable cause) {
            super(message, cause);
        }
    public NotProceedingConference(String message) {
         super(message);
    }
    public NotProceedingConference(Throwable cause) {
            super(cause);
        }
}
