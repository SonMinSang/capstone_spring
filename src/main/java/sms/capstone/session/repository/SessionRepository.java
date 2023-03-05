package sms.capstone.session.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sms.capstone.session.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
