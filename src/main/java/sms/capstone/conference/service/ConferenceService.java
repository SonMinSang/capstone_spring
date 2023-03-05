package sms.capstone.conference.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sms.capstone.conference.Conference;
import sms.capstone.conference.dto.ConferenceElement;
import sms.capstone.conference.exception.NotConferenceHostException;
import sms.capstone.conference.exception.NotConferenceMemberException;
import sms.capstone.conferenceuser.repository.ConferenceUserRepository;
import sms.capstone.conferenceuser.service.ConferenceUserService;
import sms.capstone.session.Session;
import sms.capstone.conference.repository.ConferenceRepository;
import sms.capstone.user.User;
import sms.capstone.conference.dto.ConferenceEdit;
import sms.capstone.conference.dto.ConferenceCreate;
import sms.capstone.global.dto.SessionInput;
import sms.capstone.user.repository.UserRepository;
import sms.capstone.global.util.SecurityUtil;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class ConferenceService {
    private final ConferenceRepository conferenceRepository;
    private final ConferenceUserRepository conferenceUserRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ConferenceElement> getAllConferences() {

        List<Conference> conferences = conferenceRepository.findAllWithSession();
        List<ConferenceElement> result = conferences.stream()
                .map(ConferenceElement::new)
                .collect(toList());
        return result;
    }

    public ConferenceCreate registerConference(ConferenceCreate conferenceCreate){
        User user = SecurityUtil.getCurrentEmail().flatMap(userRepository::findOneByEmail).orElse(null);
        Conference conference = Conference.builder()
                .user(user)
                .name(conferenceCreate.getName())
                .content(conferenceCreate.getContent())
                .startDate(conferenceCreate.getStartDate())
                .endDate(conferenceCreate.getEndDate())
                .maxPeople(conferenceCreate.getMaxPeople())
                .build();
        for (SessionInput sessionInput : conferenceCreate.getSessions()) {
            Session session = new Session(
                    sessionInput.getSessionContent(),
                    sessionInput.getStartDate(),
                    sessionInput.getEndDate());
            conference.addSession(session);
        }
        conferenceRepository.save(conference);
        user.registerConference(conference);
        return conferenceCreate;
    }

    public void editConference(ConferenceEdit conferenceEdit) {
        User user = SecurityUtil.getCurrentEmail().flatMap(userRepository::findOneByEmail).orElse(null);
        Conference conference = conferenceRepository.findById(conferenceEdit.getConferenceId());
        conference.edit(conferenceEdit);
    }

    @Transactional(readOnly = true)
    public void validConferenceMember(Long sessionId){
        User user = SecurityUtil.getCurrentEmail().flatMap(userRepository::findOneByEmail).orElse(null);
        Conference conference = conferenceRepository.findBySessionId(sessionId);
        if (conferenceUserRepository.findConferenceWithUser(conference.getId(), user.getId()) == null)
            throw new NotConferenceMemberException("컨퍼런스의 멤버가 아닙니다.");
    }
    @Transactional(readOnly = true)
    public void validConferenceHost(Long sessionId){
        User user = SecurityUtil.getCurrentEmail().flatMap(userRepository::findOneByEmail).orElse(null);
        Conference conference = conferenceRepository.findBySessionId(sessionId);
        if (conference.getUser().getId() != user.getId()){
            throw new NotConferenceHostException("컨퍼런스의 호스트가 아닙니다.");
        }
    }
}
