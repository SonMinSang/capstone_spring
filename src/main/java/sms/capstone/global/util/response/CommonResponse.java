package sms.capstone.global.util.response;

import lombok.Getter;

@Getter
public class CommonResponse {
    boolean success;
    public CommonResponse(boolean success) {
        this.success = success;
    }
}
