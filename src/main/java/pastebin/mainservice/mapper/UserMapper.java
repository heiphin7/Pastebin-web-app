package pastebin.mainservice.mapper;

import org.springframework.stereotype.Service;
import pastebin.mainservice.dto.RegistrationUserDto;
import pastebin.mainservice.entity.User;

@Service
public class UserMapper {


    public User registrationToUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }
}
