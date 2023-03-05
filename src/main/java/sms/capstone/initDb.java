package sms.capstone;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sms.capstone.conference.Conference;
import sms.capstone.session.Session;
import sms.capstone.user.Gender;
import sms.capstone.user.User;
import sms.capstone.user.dto.UserSignUpDto;
import sms.capstone.user.service.UserService;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static sms.capstone.conference.Template.CLASSROOM;

@Component
@RequiredArgsConstructor
class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){

        initService.userInit1();
        initService.userInit2();
        initService.dbInit1();
        initService.dbInit2();
        initService.dbInit3();
        initService.dbInit4();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final UserService userService;

        public void userInit1(){
            UserSignUpDto user = UserSignUpDto.builder()
                    .email("sms7624@gmail.com")
                    .nickname("민상")
                    .password("aaaaaa1!")
                    .gender(Gender.MALE)
                    .name("손민상")
                    .phoneNumber("010-2528-7624")
                    .build();
            userService.addUser(user);
        }

        public void userInit2(){
            UserSignUpDto user = UserSignUpDto.builder()
                    .email("smsun4@gmail.com")
                    .nickname("민선")
                    .name("송민선")
                    .gender(Gender.MALE)
                    .password("aaaaaa1!")
                    .phoneNumber("010-2528-7624")
                    .build();
            userService.addUser(user);
        }

        public void dbInit1(){
            User user = em.find(User.class, 1L);

            Conference conference = Conference.builder()
                    .user(user)
                    .name("itm 겨울학회")
                    .content("한학년을 마무리 하기 전 학회를 진행한다.")
                    .startDate(LocalDateTime.parse("2022-12-12T12:00"))
                    .endDate(LocalDateTime.parse("2022-12-12T12:00"))
                    .maxPeople(0)
                    .build();
            Session session = new Session("한학년을 마무리 하기 전 학회를 진행한다.", LocalDateTime.parse("2022-12-12T12:00"), LocalDateTime.parse("2022-12-12T15:00"));
            conference.addSession(session);
            em.persist(conference);
        }

        public void dbInit2(){
            User user = em.find(User.class, 1L);

            Conference conference = Conference.builder()
                    .user(user)
                    .name("itm 겨울학회")
                    .content("한학년을 마무리 하기 전 학회를 진행한다.")
                    .startDate(LocalDateTime.parse("2022-12-12T12:00"))
                    .endDate(LocalDateTime.parse("2022-12-12T12:00"))
                    .maxPeople(50)
                    .build();
            Session session = new Session("한학년을 마무리 하기 전 학회를 진행한다.", LocalDateTime.parse("2022-12-12T12:00"), LocalDateTime.parse("2022-12-12T15:00"));
            conference.addSession(session);
            em.persist(conference);
        }
        public void dbInit3(){
            User user = em.find(User.class, 1L);

            Conference conference = Conference.builder()
                    .user(user)
                    .name("itm 겨울학회")
                    .content("한학년을 마무리 하기 전 학회를 진행한다.")
                    .startDate(LocalDateTime.parse("2022-12-12T12:00"))
                    .endDate(LocalDateTime.parse("2022-12-12T12:00"))
                    .maxPeople(50)
                    .build();
            Session session = new Session("한학년을 마무리 하기 전 학회를 진행한다.", LocalDateTime.parse("2022-12-12T12:00"), LocalDateTime.parse("2022-12-12T15:00"));
            conference.addSession(session);
            em.persist(conference);
        }

        public void dbInit4(){
            User user = em.find(User.class, 1L);

            Conference conference = Conference.builder()
                    .user(user)
                    .name("itm 겨울학회")
                    .content("한학년을 마무리 하기 전 학회를 진행한다.")
                    .startDate(LocalDateTime.parse("2022-12-12T12:00"))
                    .endDate(LocalDateTime.parse("2022-12-12T12:00"))
                    .maxPeople(50)
                    .build();
            Session session = new Session("한학년을 마무리 하기 전 학회를 진행한다.", LocalDateTime.parse("2022-12-12T12:00"), LocalDateTime.parse("2022-12-12T15:00"));
            conference.addSession(session);
            em.persist(conference);
        }
    }
}
