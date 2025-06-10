package dileksoft.sdk.service.implementation;


import dileksoft.sdk.dto.RoleDTO;
import dileksoft.sdk.persistence.domain.Role;
import dileksoft.sdk.persistence.repository.RoleRepository;
import dileksoft.sdk.service.mapper.RoleMapper;
import dileksoft.sdk.service.ts.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleMapper roleMapper, RoleRepository roleRepository) {
        this.roleMapper = roleMapper;
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public RoleDTO save(RoleDTO roleDTO) {
        Role role = roleMapper.toEntity(roleDTO);
        role.setCreatedAt(OffsetDateTime.now());
      //  role.setUpdatedAt(OffsetDateTime.now());
        Role savedRole = roleRepository.save(role);
        return roleMapper.toDto(savedRole);
    }

    @Override
    public Role findById(String id) {
        return roleRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Object deleteById(String id) {
        return roleRepository.findById(id)
                .map(role -> {
                    roleRepository.delete(role);
                    return "Role deleted successfully";
                })
                .orElse("Role not found");
    }

    @Override
    public RoleDTO update(RoleDTO categoryCompanyRelationDTO) {
        Role role = roleRepository.findById(String.valueOf(categoryCompanyRelationDTO.getRoleId())).orElse(null);
        if (role != null) {
            role = roleMapper.toEntity(categoryCompanyRelationDTO);
          //  role.setUpdatedAt(OffsetDateTime.now());
            role = roleRepository.save(role);
            return roleMapper.toDto(role);
        }
        return null;
    }

    @Override
    public Page<Role> findAll(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }
    // Additional query methods can be defined here if needed
}
