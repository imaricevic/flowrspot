package povio.flowrspot.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Flower")
public class Flower {

    @Id
    @GeneratedValue
    @Column(name = "flower_id", nullable = false)
    private Long id;

    @Column(name = "flower_name", nullable = false, length = 55)
    private String name;

    @Column(name = "flower_image", length = 55)
    private String image;

    @Column(name = "flower_description", length = 255)
    private String description;

    @OneToMany(mappedBy = "flower")
    private List<Sighting> listOfSightings;

}
