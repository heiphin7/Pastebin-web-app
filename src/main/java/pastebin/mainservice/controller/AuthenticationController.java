package pastebin.mainservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pastebin.mainservice.dto.AuthenticationRequestDto;
import pastebin.mainservice.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    // future endpoint for authenticate user
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(AuthenticationRequestDto userDto) {
        return ResponseEntity.ok(authenticationService.authenticateUser(userDto));
    }
}
