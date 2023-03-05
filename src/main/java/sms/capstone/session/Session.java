package sms.capstone.session;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import sms.capstone.conference.Conference;
import sms.capstone.conference.Template;
import sms.capstone.sessionfile.SessionFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Session{
    @Id
    @GeneratedValue
    @Column(name = "session_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @Enumerated(EnumType.STRING)
    private Template templateType;
    @Lob
    private String sessionContent;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "session", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<SessionFile> sessions;
    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public Session(String sessionContent, LocalDateTime startDate, LocalDateTime endDate){
        this.sessionContent = sessionContent;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public void uploadFile(SessionFile sessionFile){
        this.sessions.add(sessionFile);
        sessionFile.register(this);

    }

}
