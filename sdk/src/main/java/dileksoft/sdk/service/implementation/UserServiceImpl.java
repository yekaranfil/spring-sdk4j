package dileksoft.sdk.service.implementation;

import dileksoft.sdk.dto.UserDTO;
import dileksoft.sdk.persistence.domain.User;
import dileksoft.sdk.persistence.repository.UserRepository;
import dileksoft.sdk.service.mapper.UserMapper;
import dileksoft.sdk.service.ts.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository, UserMapper userMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        if (!user.getPasswordHash().startsWith("$2a$")) {
            user.setPasswordHash(bCryptPasswordEncoder.encode(user.getPasswordHash()));
        }
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());

        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public Object delete(String id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return user;
                })
                .orElse(null);
    }

    @Override
    public Page<User> findAllWithPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
        User user = userRepository.findById(String.valueOf(userDTO.getUserId())).orElse(null);
        if (user != null) {
            user = userMapper.toEntity(userDTO);
            user.setUpdatedAt(OffsetDateTime.now());
            user = userRepository.save(user);
            return userMapper.toDto(user);
        }
        return null;
    }
}
