package pastebin.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pastebin.mainservice.dto.AuthenticationRequestDto;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    public String authenticateUser(AuthenticationRequestDto dto) {

        try {

            // Аутентификация пользовталея с помощью сервиса authentication
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword()
                    )
            );

            // Установка аутентифицированного пользователя в контекст безопасности
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "Авторизация прошла успешно!";
        } catch (BadCredentialsException ex) {
            return "Имя пользователя или пароль неправильный!";
        }catch (Exception e){
            return "Имя пользователя не найдено!";
        }

    }

}
