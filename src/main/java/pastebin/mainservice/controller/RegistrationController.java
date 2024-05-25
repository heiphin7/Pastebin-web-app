package pastebin.mainservice.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pastebin.mainservice.dto.RegistrationUserDto;
import pastebin.mainservice.service.RegistrationService;

@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    // TODO CUSTOM ERROR & SUCCES ENTITES FOR HTTP RESPONSE

    @PostMapping("/save")
    public ResponseEntity<?> saveNewUser(@RequestBody RegistrationUserDto userDto) {
        try {
            return ResponseEntity.ok(registrationService.saveUser(userDto));
        } catch (NullPointerException exception) {
            return new ResponseEntity<>("Все поля должны быть заполнены!", HttpStatus.BAD_REQUEST);
        } catch (BadCredentialsException exception) {
            return new ResponseEntity<>("Пароли не совпадают", HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (IllegalArgumentException exception) {
            return new ResponseEntity<>("Введите корректную почту", HttpStatus.NOT_ACCEPTABLE);
        } catch (BadRequestException exception) {
            return new ResponseEntity<>("Имя пользователя уже занято!", HttpStatus.CONFLICT);
        } catch (Exception exception) {
            return new ResponseEntity<>("Произошла какая-то ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
