package dileksoft.sdk.service.ts;


import dileksoft.sdk.dto.UserDTO;
import dileksoft.sdk.persistence.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<User> getAll();

    User findById(String id);

    UserDTO save(UserDTO userDTO);

    Object delete(String id);

    Page<User> findAllWithPage(Pageable pageable);

    UserDTO update(UserDTO userDTO);
}
