package sms.capstone.conferenceuser.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sms.capstone.conferenceuser.ConferenceUser;
import sms.capstone.user.User;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ConferenceUserRepository {

    private final EntityManager em;

    public void save(ConferenceUser conferenceUser){
        em.persist(conferenceUser);
    }
    public Optional<ConferenceUser> findConferenceWithUser(Long conferenceId, Long userId) {
        return em.createQuery(
                        "select cu from ConferenceUser cu" +
                                " where cu.user = "+ userId +
                                " AND cu.conference = " + conferenceId , ConferenceUser.class)
                .getResultList()
                .stream()
                .findAny();
    }

}
