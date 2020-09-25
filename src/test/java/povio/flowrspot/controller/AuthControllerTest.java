package povio.flowrspot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import povio.flowrspot.dto.AuthenticationRequestDTO;
import povio.flowrspot.model.User;
import povio.flowrspot.repository.UserRepository;
import povio.flowrspot.security.JwtRequestFilter;
import povio.flowrspot.security.JwtUtil;
import povio.flowrspot.service.CustomUserDetailsService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    PasswordEncoder passwordEncoder;
    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    JwtRequestFilter jwtRequestFilter;
    @MockBean
    JwtUtil jwtUtil;
    @MockBean
    CustomUserDetailsService userDetailsService;

    private String expectedToken =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjAwOTg2NzYwfQ" +
                    ".aEJccmvQTL51dE_hG5LmD9A5L2RPFJ8LGuSUPI78a2zxRL7BeJsDhsFvy0yIUWnPXFofg4ycSvcYQ3ztiQAzpw";

    @Test
    void registerTest() throws Exception {
        User user = new User(null, "test@mail.com", "test", "test", null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON);

        // bug in WebMvcTest; @jsonProperty not working properly
        String encoded = passwordEncoder.encode(user.getPassword());
        when(passwordEncoder.encode(anyString())).thenReturn(encoded);
        when(userRepository.existsByUsernameOrMail(anyString(), anyString())).thenReturn(false).thenReturn(true);

        mvc.perform(requestBuilder)
                .andExpect(content().string("Registration completed."))
                .andExpect(status().isOk());
    }

    @Test
    void loginTest() throws Exception {
        AuthenticationRequestDTO request = new AuthenticationRequestDTO("test", "test");
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login").secure(true)
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON);
        when(jwtUtil.generateJwtToken(request.getUsername())).thenReturn(expectedToken);

        mvc.perform(requestBuilder)
                .andExpect(jsonPath("$.jwt").value(expectedToken))
                .andExpect(status().isOk());
    }
}
