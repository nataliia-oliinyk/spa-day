package base.service.user.repository;

import base.service.user.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
