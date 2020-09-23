package povio.flowrspot.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Sighting")
public class Sighting {

    @Id
    @GeneratedValue
    @Column(name = "flower_id", nullable = false)
    private Long id;

    @Column(name = "sighting_long", nullable = false)
    private BigDecimal longitude;

    @Column(name = "sighting_lat", nullable = false)
    private BigDecimal latitude;

    @Column(name = "sighting_image", length = 95)
    private String image;

    @Column(name = "sighting_quote", length = 255)
    private String quote;

    @ManyToOne
    @JoinColumn(name = "sighting_user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "sighting_flower_id", referencedColumnName = "flower_id", nullable = false)
    private Flower flower;

    @ManyToMany
    @JoinTable(
            name = "User_sighting_likes",
            joinColumns = @JoinColumn(name = "like_sighting_id"),
            inverseJoinColumns = @JoinColumn(name = "like_user_id"))
    private List<User> likedByUsers;
}
