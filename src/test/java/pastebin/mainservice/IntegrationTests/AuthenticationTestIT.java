package pastebin.mainservice.IntegrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pastebin.mainservice.dto.AuthenticationRequestDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false) // Для просмотра запросы/ответы в тестах
class AuthenticationTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void AuthenticateUserWithSuccesCredentionals() throws Exception{
        // given

        // example user for authentication
        AuthenticationRequestDto dto = new AuthenticationRequestDto();
        dto.setUsername("heiphin7");
        dto.setPassword("12341234");

        String requestDtoJSON = objectMapper.writeValueAsString(dto);

        // when
        mockMvc.perform(post("/v1/authentication/authenticate")
                // send AuthenticationRequestDto to endpoint, and set type to json
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestDtoJSON))

                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),

                        // check all parametres of json response
                        jsonPath("$.message").value("Авторизация прошла успешно!"),
                        jsonPath("$.httpStatus").value(200),
                        jsonPath("$.path").value("/api/v1/authentication/authenticate"),
                        jsonPath("$.errorType").value("Success")
                );
    }

    @Test
    void AuthenticateUserWithWrongPassword() throws Exception {
        //given
        // user in first test case, but with wrong password
        AuthenticationRequestDto dto = new AuthenticationRequestDto();
        dto.setUsername("heiphin7");
        dto.setPassword("wrongPassword");

        // convert dto to JSON
        String requestDtoJson = objectMapper.writeValueAsString(dto);

        // when
        mockMvc.perform(post("/v1/authentication/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestDtoJson))

                // then
         .andExpectAll(
                status().isUnauthorized(), // we wait unauthorized, cause: wrong password
                content().contentType(MediaType.APPLICATION_JSON),

                // check all parametres of json response
                jsonPath("$.message").value("Неправильный пароль!"),
                jsonPath("$.httpStatus").value(401),
                jsonPath("$.path").value("/api/v1/authentication/authenticate"),
                jsonPath("$.errorType").value("AuthenticationError"));
    }

    @Test
    void AuthenticateUsernameNotFound() throws Exception{
        // given
        AuthenticationRequestDto dto = new AuthenticationRequestDto();
        // user, not exist in db
        dto.setUsername("aslkdfja;djfn;kawjdknf;alksjd;alkwjsd;alwkjdfefg");
        dto.setPassword("some password");

        // convert dto to JSON
        String requestDtoJson = objectMapper.writeValueAsString(dto);

        // when
        mockMvc.perform(post("/v1/authentication/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestDtoJson))

                // then
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),

                        // check all parametres of json response
                        jsonPath("$.message").value("Имя пользователя не найдено!"),
                        jsonPath("$.httpStatus").value(400),
                        jsonPath("$.path").value("/api/v1/authentication/authenticate"),
                        jsonPath("$.errorType").value("NotFoundError")
                );
    }
}