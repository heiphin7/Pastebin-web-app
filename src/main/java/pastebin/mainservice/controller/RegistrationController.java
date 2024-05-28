package pastebin.mainservice.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pastebin.mainservice.dto.RegistrationUserDto;
import pastebin.mainservice.error.ApplicationError;
import pastebin.mainservice.service.RegistrationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/registration")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final String currentPath = "/api/v1/registration";
    @PostMapping("/save")
    public ResponseEntity<?> saveNewUser(@RequestBody RegistrationUserDto userDto) {
        try {
            String message = registrationService.saveUser(userDto);

            return new ResponseEntity<>(new ApplicationError(HttpStatus.OK.value(), message, "/api/v1/registration/save"), HttpStatus.OK);
        } catch (NullPointerException exception) {
            return new ResponseEntity<>(new ApplicationError(HttpStatus.BAD_REQUEST.value(), "Все поля должны быть заполнены!", "NullError", currentPath + "/save"), HttpStatus.BAD_REQUEST);
        } catch (BadCredentialsException exception) {
            return new ResponseEntity<>(new ApplicationError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают!", "PasswordsNotMatches", currentPath + "/save"), HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(new ApplicationError(HttpStatus.BAD_REQUEST.value(), "Введите корректную почту!", "EmailError", currentPath + "/save"), HttpStatus.BAD_REQUEST);
        } catch (BadRequestException exception) {
            return new ResponseEntity<>(new ApplicationError(HttpStatus.BAD_REQUEST.value(), "Имя пользователя занято!", "UsernameIsTaken", currentPath + "/save"), HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ApplicationError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Произошла какая-то ошибка", "ServerError", currentPath + "/save"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
