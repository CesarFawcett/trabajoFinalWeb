package aeroline.nr.api.services;

import java.util.List;
import java.util.Optional;
import aeroline.nr.api.entities.User;

public interface UserService {
    User create(User user);
    Optional<User> update(Integer id, User newUser);
    List<User> findAll();
    Optional<User> findById(Integer id);
    void delete(Integer id);
    User getUserById(int userId); 
}
