package dileksoft.sdk.service.ts;


import dileksoft.sdk.dto.UserRoleRelationDTO;
import dileksoft.sdk.persistence.domain.UserRoleRelation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserRoleRelationService {

    List<UserRoleRelation> getAll();

    UserRoleRelationDTO save(UserRoleRelationDTO userRoleRelationDTO);

    UserRoleRelation findById(String id);

    Object deleteById(String id);

    Page<UserRoleRelation> findAll(Pageable pageable);

    UserRoleRelationDTO update(UserRoleRelationDTO userRoleRelationDTO);

}
