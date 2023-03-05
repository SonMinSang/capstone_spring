package sms.capstone.conference;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import sms.capstone.session.Session;
import sms.capstone.conferenceuser.ConferenceUser;
import sms.capstone.global.util.BaseTimeEntity;
import sms.capstone.user.User;
import sms.capstone.conference.dto.ConferenceEdit;
import sms.capstone.conference.exception.ExceedLimitMemberException;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "conference")
public class Conference extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "conference_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "conference")
    private List<ConferenceUser> conferenceUser;

    @Lob
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endDate;

    @Builder.Default
    @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL)
    private List<Session> sessions = new ArrayList<>();

    @Builder.Default
    private int currentPeople = 0;

    private int maxPeople;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private ConferenceStatus status = ConferenceStatus.PENDING;

    @Builder.Default
    private String videoServerId = UUID.randomUUID().toString();

    @Builder.Default
    private String chatServerId  = UUID.randomUUID().toString();

    @Builder.Default
    private String unityServerId = UUID.randomUUID().toString();

    @Builder.Default
    private String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/65/No-Image-Placeholder.svg/495px-No-Image-Placeholder.svg.png?20200912122019";

    public void addSession(Session session){
        sessions.add(session);
        session.setConference(this);
    }
    public void addMember(){
        if (currentPeople >= maxPeople){
            throw new ExceedLimitMemberException("신청 인원이 가득 차 있습니다.");
        };
        currentPeople++;
    }

    public void removeMember(){
        currentPeople--;
        status = ConferenceStatus.CANCELED;
    }

    public void edit(ConferenceEdit conferenceEdit){
        this.name = conferenceEdit.getName();
        this.content = conferenceEdit.getContent();
        this.startDate = conferenceEdit.getStartDate();
        this.endDate = conferenceEdit.getEndDate();
        this.maxPeople = conferenceEdit.getMaxPeople();
        this.imageUrl = conferenceEdit.getImageUrl();

    }
    public void denied(){
        this.status = ConferenceStatus.DENY;
    }
    public void permitted(){

        this.status = ConferenceStatus.PERMIT;
    }
}
