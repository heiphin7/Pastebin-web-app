package pastebin.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pastebin.mainservice.dto.RegistrationUserDto;
import pastebin.mainservice.entity.User;
import pastebin.mainservice.mapper.UserMapper;
import pastebin.mainservice.repo.UserRepository;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    public String saveUser(RegistrationUserDto userDto) {

        if (userDto.getUsername().isBlank() || userDto.getPassword().isBlank()
            || userDto.getEmail().isBlank() || userDto.getConfirmPassword().isEmpty()) {
            return "Все поля должны быть заполнены!";
        }

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
           return "Пароли не совпадают!";
        }

        if (userService.findByUsername(userDto.getUsername()) != null) {
            return "Имя пользователя занято!";
        }

        if (!isValidEmail(userDto.getEmail())) {
            return "Введите корректный email";
        }


        User userToSave = userMapper.registrationToUser(
                userDto.getUsername(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword())
        );

        userRepository.save(userToSave);
        return "Пользователь успешно сохранен!";
    }


    private boolean isValidEmail(String email) {
        String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])\n";
        return email.matches(EMAIL_REGEX);
    }
}
