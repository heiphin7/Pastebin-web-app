package pastebin.mainservice.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pastebin.mainservice.entity.Role;
import pastebin.mainservice.entity.User;
import pastebin.mainservice.repo.RoleRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserMapper {

    private final RoleRepository roleRepository;

    public User registrationToUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        Role defaultRole = roleRepository.findByName("ROLE_USER").orElse(null);

        if(defaultRole == null) {
            defaultRole = new Role();
            defaultRole.setId(1L);
            defaultRole.setName("ROLE_USER");
            roleRepository.save(defaultRole);
        }

        user.setRoles(List.of(defaultRole));

        return user;
    }
}
