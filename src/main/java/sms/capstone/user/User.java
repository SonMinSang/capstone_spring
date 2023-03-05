package sms.capstone.user;

import lombok.*;
import sms.capstone.authority.Authority;
import sms.capstone.conference.Conference;
import sms.capstone.conferenceuser.ConferenceUser;
import sms.capstone.user.dto.UserEditDto;
import sms.capstone.global.util.BaseTimeEntity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    private String name;

    @Column(name = "password", length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Pattern(regexp="^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$")
    private String phoneNumber;

    private LocalDateTime expiredTime;

    @OneToMany(mappedBy = "user")
    private List<ConferenceUser> conferenceUser;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade=CascadeType.REMOVE, orphanRemoval = true)
    private List<Conference> conferences = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;


    public UserEditDto update(UserEditDto userEditDto){
        this.nickname = userEditDto.getNickname();
        this.phoneNumber = userEditDto.getPhoneNumber();
        return userEditDto;
    }

    public void registerConference(Conference conference){
        this.conferences.add(conference);
        conference.setUser(this);
    }
}
