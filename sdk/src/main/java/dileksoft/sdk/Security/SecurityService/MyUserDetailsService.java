package dileksoft.sdk.Security.SecurityService;

import dileksoft.sdk.persistence.domain.User;
import dileksoft.sdk.persistence.domain.UserRoleRelation;
import dileksoft.sdk.persistence.repository.UserRepository;
import dileksoft.sdk.persistence.repository.UserRoleRelationRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRelationRepository userRoleRepository;

    public MyUserDetailsService(UserRepository userRepository, UserRoleRelationRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + username));

        List<UserRoleRelation> userRoles = userRoleRepository.findByUser_UserId(user.getUserId());
        List<SimpleGrantedAuthority> authorities = userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.getRole().getRoleName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPasswordHash(),
                authorities
        );
    }
}