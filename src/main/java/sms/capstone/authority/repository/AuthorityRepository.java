package sms.capstone.authority.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import sms.capstone.authority.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
