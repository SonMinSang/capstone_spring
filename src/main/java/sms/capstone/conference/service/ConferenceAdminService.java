package sms.capstone.conference.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sms.capstone.conference.Conference;
import sms.capstone.conference.repository.ConferenceRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ConferenceAdminService {
    private final ConferenceRepository conferenceRepository;

    public void permitConference(Long conferenceId){
        Conference conference = conferenceRepository.findById(conferenceId);
        conference.permitted();
    }

    public void denyConference(Long conferenceId){
        Conference conference = conferenceRepository.findById(conferenceId);
        conference.denied();
    }
    public void deleteConference(Long conferenceId){
        Conference conference = conferenceRepository.findById(conferenceId);
        conferenceRepository.delete(conference);
    }
}
