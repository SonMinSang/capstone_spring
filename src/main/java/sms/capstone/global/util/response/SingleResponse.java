package sms.capstone.global.util.response;


import lombok.Getter;

@Getter
public class SingleResponse <T> extends CommonResponse{
    T data;
    public SingleResponse(T data){
        super(true);
        this.data = data;
    }
}
