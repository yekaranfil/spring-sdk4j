package dileksoft.sdk.persistence.repository;

import dileksoft.sdk.persistence.domain.UserRoleRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRelationRepository extends JpaRepository<UserRoleRelation, String> {

    // Custom query methods can be added here if needed
    List<UserRoleRelation> findByUser_UserId(String userId);
}
