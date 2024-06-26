package pastebin.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pastebin.mainservice.dto.AuthenticationRequestDto;

import static org.springframework.data.crossstore.ChangeSetPersister.*;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailService userDetailService;

    public void authenticateUser(AuthenticationRequestDto dto) throws BadCredentialsException, NotFoundException {

            UserDetails userDetails = userDetailService.loadUserByUsername(dto.getUsername());

            if(userDetails == null) {
                throw new NotFoundException();
            }

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
    }
}
