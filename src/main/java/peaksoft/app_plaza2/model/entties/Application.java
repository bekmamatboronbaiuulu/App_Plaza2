package peaksoft.app_plaza2.model.entties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import peaksoft.app_plaza2.model.enums.Status;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "applications")
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;
    String name;
    String description;
    String developer;
    String version;
    @Enumerated(EnumType.STRING)
    Status appStatus;
    String genreName;
    LocalDate createDate;
    @Transient
    private Long genreId;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "genre_id")
    @JsonIgnore
    Genre genre;


    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, mappedBy = "applications")
    List<User> users;
}