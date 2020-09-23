package povio.flowrspot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import povio.flowrspot.model.Sighting;
import povio.flowrspot.model.User;

@RepositoryRestResource(collectionResourceRel = "sightings", path = "sighting")
public interface SightingRepository extends JpaRepository<Sighting, Long> {
}
