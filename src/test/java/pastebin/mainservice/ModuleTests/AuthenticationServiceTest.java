// TODO ПЕРЕДЕЛАТЬ ТЕСТ ПО VOID МЕТОД

//package pastebin.mainservice.ModuleTests;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import pastebin.mainservice.controller.AuthenticationController;
//import pastebin.mainservice.dto.AuthenticationRequestDto;
//import pastebin.mainservice.service.AuthenticationService;
//
//@SpringBootTest
//class AuthenticationServiceTest {
//
//    @Mock
//    private AuthenticationService authenticationService;
//
//    @InjectMocks
//    private AuthenticationController authenticationController;
//
//    @Test
//    void Authentication_ReturnsSuccesAuthentication() throws Exception{
//        // given
//        AuthenticationRequestDto dto = new AuthenticationRequestDto();
//        dto.setUsername("testUser");
//        dto.setPassword("testPassword");
//        // Имитация поведения мока, в данном случае authenticationSerivce
//        Mockito.when(authenticationService.authenticateUser(dto)).thenReturn("Авторизация прошла успешно!");
//
//        // when
//        ResponseEntity<?> responseEntity = this.authenticationController.authenticate(dto);
//
//        // then (Сравнение результатов с ожиадемыми)
//        Assertions.assertNotNull(responseEntity);
//        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        Assertions.assertEquals("Авторизация прошла успешно!", responseEntity.getBody());
//    }
//}
