package pastebin.mainservice.mapper;

import org.springframework.stereotype.Service;
import pastebin.mainservice.dto.RegistrationUserDto;
import pastebin.mainservice.entity.User;

@Service
public class UserMapper {


    public User registrationToUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setUsername(registrationUserDto.getUsername());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(registrationUserDto.getPassword());

        return user;
    }
}
