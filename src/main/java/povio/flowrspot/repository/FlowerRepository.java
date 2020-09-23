package povio.flowrspot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import povio.flowrspot.model.Flower;
import povio.flowrspot.model.User;

@RepositoryRestResource(collectionResourceRel = "flowers", path = "flower")
public interface FlowerRepository extends JpaRepository<Flower, Long> {
}
