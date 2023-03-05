package sms.capstone.conference.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sms.capstone.conference.Conference;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceServerIdDto {

    String videoServerId;
    String chatServerId;
    String unityServerId;
    public ConferenceServerIdDto(Conference conference){
        videoServerId = conference.getVideoServerId();
        chatServerId = conference.getChatServerId();
        unityServerId = conference.getUnityServerId();
    }
}
