package sms.capstone.sessionfile;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sms.capstone.conference.Conference;
import sms.capstone.global.util.BaseTimeEntity;
import sms.capstone.session.Session;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class SessionFile extends BaseTimeEntity {

    @Id
    @Column(name = "conference_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id")
    private Session session;

    private String fileUrl;

    public SessionFile(String fileUrl){
        this.fileUrl = fileUrl;
    }
    public void register(Session session){
        this.session = session;
    }
}
