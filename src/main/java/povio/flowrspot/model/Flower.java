package povio.flowrspot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flower")
public class Flower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flower_id", nullable = false)
    private Long id;

    @Column(name = "flower_name", nullable = false, length = 55)
    private String name;

    @Column(name = "flower_image", length = 55)
    private String image;

    @Column(name = "flower_description", length = 255)
    private String description;

    @OneToMany(mappedBy = "flower")
    @JsonIgnore
    private List<Sighting> listOfSightings;

    @Transient
    public String getImageLink() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/images/" + image;
    }
}
