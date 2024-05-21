package pastebin.mainservice.dto;

import lombok.Data;

@Data
public class RegistrationUserDto {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
}
