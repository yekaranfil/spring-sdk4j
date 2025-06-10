package dileksoft.sdk.service.ts;



import dileksoft.sdk.dto.RoleDTO;
import dileksoft.sdk.persistence.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<Role> getAll();

    RoleDTO save(RoleDTO roleDTO);

    Role findById(String id);

    Object deleteById(String id);

    Page<Role> findAll(Pageable pageable);

    RoleDTO update(RoleDTO userRoleDTO);
    // Additional query methods can be defined here if needed
}
