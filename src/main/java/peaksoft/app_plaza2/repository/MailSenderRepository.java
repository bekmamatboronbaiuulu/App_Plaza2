package peaksoft.app_plaza2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.app_plaza2.model.entties.MailSender;
@Repository
public interface MailSenderRepository extends JpaRepository<MailSender, Long> {
}