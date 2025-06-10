package dileksoft.sdk.persistence.repository;

import dileksoft.sdk.persistence.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    // Additional query methods can be defined here if needed
}
