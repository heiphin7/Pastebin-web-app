package pastebin.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pastebin.mainservice.dto.AuthenticationRequestDto;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    private final UserDetailService userDetailService;

    public String authenticateUser(AuthenticationRequestDto dto) {
        try {
            UserDetails userDetails = userDetailService.loadUserByUsername(dto.getUsername());

            // Аутентификация пользовталея с помощью сервиса authentication
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getUsername(),
                            dto.getPassword(),
                            userDetails.getAuthorities()
                    )
            );

            // Установка аутентифицированного пользователя в контекст безопасности
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "Авторизация прошла успешно!";
        } catch (BadCredentialsException ex) {
            return "Неправильный пароль!";
        }catch (Exception e){
            return "Имя пользователя не найдено!";
        }
    }
}
