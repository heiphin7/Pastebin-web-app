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

        /* Сначала идет проверка на null, затем только isBlank
        *  Так как если в isBlank закинуть null-объект, будет ошибка                                                                                                                                                    */

        if (userDto.getUsername() == null || userDto.getPassword() == null
                || userDto.getEmail() == null || userDto.getConfirmPassword() == null) {
            System.out.println(userDto.getUsername());
            System.out.println(userDto.getEmail());
            System.out.println(userDto.getPassword());
            System.out.println(userDto.getConfirmPassword());
            return "Все поля должны быть заполнены!";
        }

        if (userDto.getUsername().isBlank() || userDto.getPassword().isBlank()
            || userDto.getEmail().isBlank() || userDto.getConfirmPassword().isBlank()) {
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
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(EMAIL_REGEX);
    }
}
