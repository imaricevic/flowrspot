package povio.flowrspot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import povio.flowrspot.model.User;

@RepositoryRestResource(collectionResourceRel = "users", path = "user")
public interface UserRepository extends JpaRepository<User, Long> {
}
