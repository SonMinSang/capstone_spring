package sms.capstone.sessionfile.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sms.capstone.session.Session;
import sms.capstone.session.repository.SessionRepository;
import sms.capstone.sessionfile.SessionFile;
import sms.capstone.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionFileService {
    private final SessionRepository sessionRepository;


    @Transactional(readOnly = true)
    public List<SessionFile> getSessionFiles(Long sessionId) {

        Session session = sessionRepository.findById(sessionId).orElse(null);
        return session.getSessions();
    }
    @Transactional
    public void uploadSessionFiles(Long sessionId, List<String> files) {

        Session session = sessionRepository.findById(sessionId).orElse(null);
        for (String file : files) {
            session.uploadFile(new SessionFile(file));
        }
    }

}
