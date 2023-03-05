package sms.capstone.conferenceuser.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sms.capstone.conference.Conference;
import sms.capstone.conference.repository.ConferenceRepository;
import sms.capstone.conferenceuser.ConferenceUser;
import sms.capstone.conferenceuser.repository.ConferenceUserRepository;
import sms.capstone.global.util.SecurityUtil;
import sms.capstone.user.User;
import sms.capstone.user.repository.UserRepository;

@Transactional
@RequiredArgsConstructor
@Service
public class ConferenceUserService {
    private final ConferenceUserRepository conferenceUserRepository;
    private final ConferenceRepository conferenceRepository;
    private final UserRepository userRepository;

    public void enrollConference(Long conferenceId){
        User user = SecurityUtil.getCurrentEmail().flatMap(userRepository::findOneByEmail).orElse(null);
        Conference conference = conferenceRepository.findById(conferenceId);
        ConferenceUser conferenceUser = conferenceUserRepository.findConferenceWithUser(conferenceId, user.getId()).orElse(null);
        if (conferenceUser != null)
            conferenceUser.register();
        else {
            ConferenceUser newConferenceUser = new ConferenceUser(user, conference);
            conferenceUserRepository.save(newConferenceUser);
            newConferenceUser.getConference().addMember();
        }
    }

    public void cancelEnrollment(Long conferenceId) {
        User user = SecurityUtil.getCurrentEmail().flatMap(userRepository::findOneByEmail).orElse(null);
        ConferenceUser conferenceUser = conferenceUserRepository.findConferenceWithUser(conferenceId, user.getId()).orElse(null);
        conferenceUser.cancel();
    }
    @Transactional(readOnly = true)
    public boolean validationParticate(Long conferenceId, User user){
        ConferenceUser conferenceUser = conferenceUserRepository.findConferenceWithUser(conferenceId, user.getId()).orElse(null);
        if (conferenceUser == null) {
            return (true);
        }
        else {
            return false;
        }
    }
}
