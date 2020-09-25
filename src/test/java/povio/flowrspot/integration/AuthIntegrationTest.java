package povio.flowrspot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import povio.flowrspot.model.User;
import povio.flowrspot.repository.UserRepository;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AuthIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(null, "test@mail.com", "test", "test", null);
    }

    @Test
    void registrationTest() throws Exception {
        // workaround.. bug in test enviroment; @jsonProperty not working properly
        String encoded = passwordEncoder.encode(user.getPassword());
        when(passwordEncoder.encode(anyString())).thenReturn(encoded);
        Answer<User> answer = new Answer<User>() {
            public User answer(InvocationOnMock invocation) throws Throwable {
                return userRepository.save(user);
            }
        };
        when(userRepository.save(user)).then(answer);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/register")
                .content(new ObjectMapper().writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder).andExpect(content().string("Registration completed."))
                .andExpect(status().isOk());
    }

}