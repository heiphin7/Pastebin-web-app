package pastebin.mainservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pastebin.mainservice.dto.AuthenticationRequestDto;
import pastebin.mainservice.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    // TODO CUSTOM ERROR & SUCCES ENTITES FOR HTTP RESPONSE

    // future endpoint for authenticate user
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDto userDto) {
        try {
            return ResponseEntity.ok(authenticationService.authenticateUser(userDto));
        } catch (BadCredentialsException exception) {
            return new ResponseEntity<>("Неправильный пароль!", HttpStatus.UNAUTHORIZED);
        } catch (ChangeSetPersister.NotFoundException exception) {
            return new ResponseEntity<>("Имя пользователя не найден!", HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            return new ResponseEntity<>("Произошла какая-то ошибка", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
