package sms.capstone.conference.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sms.capstone.conference.Conference;
import sms.capstone.sessionfile.SessionFile;
import sms.capstone.conferenceuser.RegisterStatus;
import sms.capstone.conferenceuser.ConferenceUser;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ConferenceRepository {

    private final EntityManager em;

    public List<Conference> findAll(){
        return em.createQuery("select from Conference c", Conference.class)
                .getResultList();
    }
    public void save(Conference conference){
        em.persist(conference);
    }

    public void delete(Conference conference){em.remove(conference);}

    public Conference findById(Long conference_id){
        return em.createQuery(
                        "select c from Conference c" +
                                " join fetch c.user u", Conference.class)
                .getSingleResult();
    }

    public List<Conference> findAllWithSession() {
        return em.createQuery(
                "select distinct c from Conference c" +
                        " join fetch c.sessions s" +
                        " join fetch c.user u", Conference.class)
                .getResultList();
    }

    public List<ConferenceUser> findAllRegisteredConferences(Long userId) {
        return em.createQuery(
                "select cu from ConferenceUser cu" +
                        " join fetch cu.conference" +
                        " where cu.user = "+ userId +
                        " AND cu.status = :status",
                        ConferenceUser.class)
                .setParameter("status", RegisterStatus.REGISTERED)
                .getResultList();
    }

    public List<SessionFile> findAllConferenceFiles(Long conferenceId) {
        return em.createQuery(
                        "select * from ConferenceFile cf" +
                                " where cf.conference = "+ conferenceId,
                        SessionFile.class)
                .getResultList();
    }

    public List<Conference> findAllHostConference(Long userId) {
        return em.createQuery(
                        "select c from Conference C" +
                                " join fetch u.conference" +
                                " where c.user = "+ userId
                        ,Conference.class)
                .getResultList();
    }


    public Conference findBySessionId(Long sessionId) {
        return em.createQuery(
                        "select distinct c from Conference C" +
                                " join c.sessions" +
                                " where c.sessions = "+ sessionId
                        ,Conference.class)
                .getSingleResult();
    }


}