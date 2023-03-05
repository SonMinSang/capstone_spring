package sms.capstone.global.util.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ListResponse <T> extends CommonResponse{
    List<T> dataList;

    public ListResponse(List<T> dataList){
        super(true);
        this.dataList = dataList;
    }
}
