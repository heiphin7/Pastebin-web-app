package pastebin.mainservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        return ResponseEntity.ok(registrationService.saveUser(userDto));
    }

}
