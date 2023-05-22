package aeroline.nr.api.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import aeroline.nr.api.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(int id);
    Optional<User> findByUsername(String username);
}

