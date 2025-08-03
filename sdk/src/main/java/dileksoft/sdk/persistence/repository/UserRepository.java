package dileksoft.sdk.persistence.repository;

import com.dileksoft.data.repository.CoreRepository;
import dileksoft.sdk.persistence.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CoreRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findByUsername(String username);

}
