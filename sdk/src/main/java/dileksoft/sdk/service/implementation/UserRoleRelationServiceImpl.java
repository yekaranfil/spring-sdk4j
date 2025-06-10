package dileksoft.sdk.service.implementation;


import dileksoft.sdk.dto.UserRoleRelationDTO;
import dileksoft.sdk.persistence.domain.UserRoleRelation;
import dileksoft.sdk.persistence.repository.UserRoleRelationRepository;
import dileksoft.sdk.service.mapper.UserRoleRelationMapper;
import dileksoft.sdk.service.ts.UserRoleRelationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class UserRoleRelationServiceImpl implements UserRoleRelationService {


    private final UserRoleRelationMapper userRoleRelationMapper;
    private final UserRoleRelationRepository userRoleRelationRepository;

    public UserRoleRelationServiceImpl(UserRoleRelationMapper userRoleRelationMapper, UserRoleRelationRepository userRoleRelationRepository) {
        this.userRoleRelationMapper = userRoleRelationMapper;
        this.userRoleRelationRepository = userRoleRelationRepository;
    }

    @Override
    public List<UserRoleRelation> getAll() {
        return userRoleRelationRepository.findAll();
    }

    @Override
    public UserRoleRelationDTO save(UserRoleRelationDTO userRoleDTO) {
        UserRoleRelation userRole = userRoleRelationMapper.toEntity(userRoleDTO);
        userRole.setCreatedAt(OffsetDateTime.now());
        userRole.setUpdatedAt(OffsetDateTime.now());
        UserRoleRelation savedUserRole = userRoleRelationRepository.save(userRole);
        return userRoleRelationMapper.toDto(savedUserRole);
    }

    @Override
    public UserRoleRelation findById(String id) {
        return userRoleRelationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("UserRole not found with id: " + id));
    }

    @Override
    public Object deleteById(String id) {
        return userRoleRelationRepository.findById(id)
                .map(userRole -> {
                    userRoleRelationRepository.delete(userRole);
                    return "UserRole deleted successfully";
                })
                .orElseThrow(() -> new RuntimeException("UserRole not found with id: " + id));
    }

    @Override
    public Page<UserRoleRelation> findAll(Pageable pageable) {
        return userRoleRelationRepository.findAll(pageable);
    }

    @Override
    public UserRoleRelationDTO update(UserRoleRelationDTO userRoleRelationDTO) {
        return null;
    }
}
