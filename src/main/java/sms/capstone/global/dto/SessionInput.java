package sms.capstone.global.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import sms.capstone.session.Session;

import javax.persistence.Lob;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class SessionInput {

    private Long id;
    @Lob
    private String sessionContent;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;

    public SessionInput(Session session) {

        id = session.getId();

        sessionContent = session.getSessionContent();

        startDate = session.getStartDate();

        endDate = session.getEndDate();
    }
}
