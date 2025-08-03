package dileksoft.sdk.service.ts;

import dileksoft.sdk.persistence.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<User> getAll();

    User findById(String id);

    Object delete(String id);

    Page<User> filter(String id, String username, String email, String firstName, String lastName, Pageable pageable);

    Page<User> findAllWithPage(Pageable pageable);

}
