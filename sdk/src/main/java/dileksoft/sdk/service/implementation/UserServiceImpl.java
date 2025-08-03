package dileksoft.sdk.service.implementation;

import com.dileksoft.data.query.CoreQueryUtils;
import com.querydsl.core.BooleanBuilder;
import dileksoft.sdk.persistence.domain.QUser;
import dileksoft.sdk.persistence.domain.User;
import dileksoft.sdk.persistence.repository.UserRepository;
import dileksoft.sdk.service.ts.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public Object delete(String id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return user;
                })
                .orElse(null);
    }

    @Override
    public Page<User> filter(String id, String username, String email, String firstName, String lastName, Pageable pageable) {


        QUser qUser = QUser.user;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        CoreQueryUtils.addIfNotBlank(booleanBuilder,  id, qUser.userId::eq);
        CoreQueryUtils.addIfNotBlank(booleanBuilder,  username, qUser.username::eq);

        return userRepository.findAll(booleanBuilder, pageable);

    }

    @Override
    public Page<User> findAllWithPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

}
