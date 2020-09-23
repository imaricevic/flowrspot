package povio.flowrspot.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "user_email", nullable = false, length = 55)
    private String mail;

    @Column(name = "user_username", nullable = false, length = 55)
    private String username;

    @Column(name = "user_password", nullable = false, length = 55)
    private String password;

    @ManyToMany(mappedBy = "likedByUsers")
    private List<Sighting> sightingsLiked;
}
