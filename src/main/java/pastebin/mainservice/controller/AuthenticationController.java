package pastebin.mainservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pastebin.mainservice.dto.AuthenticationRequestDto;
import pastebin.mainservice.error.ApplicationError;
import pastebin.mainservice.service.AuthenticationService;

import static org.springframework.data.crossstore.ChangeSetPersister.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/authentication/")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private static final String currentPath = "/api/v1/authentication";

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDto userDto) {
        try {
            authenticationService.authenticateUser(userDto);
            return new ResponseEntity<>(new ApplicationError(HttpStatus.OK.value(), "Авторизация прошла успешно!", currentPath + "/authenticate"), HttpStatus.OK);
        } catch (BadCredentialsException exception) {
            return new ResponseEntity<>(new ApplicationError(HttpStatus.UNAUTHORIZED.value(), "Неправильный пароль!", "AuthenticationError", currentPath + "/authenticate"), HttpStatus.UNAUTHORIZED);
        } catch (UsernameNotFoundException exception) {
            return new ResponseEntity<>(new ApplicationError(HttpStatus.BAD_REQUEST.value(), "Имя пользователя не найдено!", "NotFoundError", currentPath + "/authenticate" ), HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            return new ResponseEntity<>(new ApplicationError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Произошла какая-то ошибка: " + exception, "ServerError", currentPath + "/authenticate"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}