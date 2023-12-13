package peaksoft.app_plaza2.model.entties;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "mail_senders")
@AllArgsConstructor
public class MailSender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String sender;
    String text;
    LocalDate createDate;
}
