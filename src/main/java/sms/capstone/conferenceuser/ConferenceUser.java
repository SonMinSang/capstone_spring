package sms.capstone.conferenceuser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sms.capstone.conference.Conference;
import sms.capstone.conferenceuser.exception.DuplicateApplyConference;
import sms.capstone.user.User;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConferenceUser {

    @Id
    @GeneratedValue
    @Column(name = "conference_user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Conference conference;
    @Enumerated(EnumType.STRING)
    private RegisterStatus status;

    public ConferenceUser(User user, Conference conference){

        this.user = user;
        this.conference = conference;
        this.status = RegisterStatus.REGISTERED;
    }
    public void cancel(){
        this.status = RegisterStatus.CANCELLED;
        this.conference.removeMember();
    }

    public void register() {
        if(this.status != RegisterStatus.REGISTERED) {
            this.status = RegisterStatus.REGISTERED;
            this.conference.addMember();
        }
        else
            throw new DuplicateApplyConference("이미 등록된 회의입니다.");
    }
}
