package pastebin.mainservice.IntegrationTests;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pastebin.mainservice.controller.AuthenticationController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false) // Для того, чтобы мы могли просматривать запросы/ответы в тестах
class AuthenticationTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationController authenticationController;

    @Test
    void Authenticate_ReturnsSuccesAuthentication() throws Exception{
        // given
        RequestBuilder requestBuilder = get("/authenticate");

        // when
        this.mockMvc.perform(requestBuilder)
                 // then
                .andExpectAll(
                    status().isOk(), // HTTP 200 OK
                            content().contentType(MediaType.APPLICATION_JSON),
                            content().json("""
                                    [
                                        {
                                            "String": "user"
                                        },
                                            
                                        {
                                        
                                        }
                                    ]
                                    
                                    """)
                );
    }

}
