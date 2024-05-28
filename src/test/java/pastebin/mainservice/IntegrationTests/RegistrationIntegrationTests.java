package pastebin.mainservice.IntegrationTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pastebin.mainservice.dto.RegistrationUserDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class RegistrationIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void Succes_RegistrationCase() throws Exception{
        // given
        RegistrationUserDto dto = new RegistrationUserDto();
        dto.setUsername("userForTestCase");
        dto.setEmail("user1@example.com");
        dto.setPassword("password");
        dto.setConfirmPassword("password");

        // convert dto to JSON
        String requestDtoJson = objectMapper.writeValueAsString(dto);

        // when
        mockMvc.perform(post("/v1/registration/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestDtoJson))

                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),

                        // check response parametres
                        jsonPath("$.message").value("Пользователь успешно сохранен!"),
                        jsonPath("$.httpStatus").value(200),
                        jsonPath("$.path").value("/api/v1/registration/save"),
                        jsonPath("$.errorType").value("Success")
                );
    }

    @Test
    void Exception_EmptyUser() throws Exception {
        // given
        RegistrationUserDto dto = new RegistrationUserDto();
        // not set params, cause is test case with empty parametres

        // convert dto to JSON
        String requestDtoJson = objectMapper.writeValueAsString(dto);

        // when
        mockMvc.perform(post("/v1/registration/save")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(requestDtoJson))

                // then
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),

                        // check json response
                        jsonPath("$.message").value("Все поля должны быть заполнены"),
                        jsonPath("$.httpStatus").value(400),
                        jsonPath("$.path").value("/api/v1/registration/save"),
                        jsonPath("$.errorType").value("NullError")
                );
    }
}
