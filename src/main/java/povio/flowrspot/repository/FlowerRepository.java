package povio.flowrspot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import povio.flowrspot.model.Flower;
import povio.flowrspot.model.User;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, Long> {
}
