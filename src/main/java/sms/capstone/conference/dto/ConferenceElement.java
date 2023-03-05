package sms.capstone.conference.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import sms.capstone.conference.Template;
import sms.capstone.conference.Conference;
import sms.capstone.global.dto.SessionInput;

import javax.persistence.Lob;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceElement {

    private Long conferenceId;

    private String name;

    private String host;
    @Lob
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;

    private Template templateType;

    private int currentPeople;

    private int maxPeople;

    private String imageUrl;

    private List<SessionInput> sessions;

    public ConferenceElement(Conference conference) {

        conferenceId = conference.getId();

        name = conference.getName();

        host = conference.getUser().getNickname();

        content = conference.getContent();

        startDate = conference.getStartDate();

        endDate = conference.getEndDate();

        currentPeople = conference.getCurrentPeople();

        maxPeople = conference.getMaxPeople();

        imageUrl = conference.getImageUrl();

        sessions = conference.getSessions().stream()
                .map(session -> new SessionInput(session))
                .collect(toList());

    }
}
