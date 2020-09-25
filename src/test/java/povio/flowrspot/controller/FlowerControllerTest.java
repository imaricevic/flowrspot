package povio.flowrspot.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import povio.flowrspot.model.Flower;
import povio.flowrspot.repository.FlowerRepository;
import povio.flowrspot.security.JwtRequestFilter;
import povio.flowrspot.service.CustomUserDetailsService;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FlowerController.class)
@AutoConfigureMockMvc(addFilters = false)
class FlowerControllerIntTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private FlowerRepository flowerRepository;
    @MockBean
    JwtRequestFilter jwtRequestFilter;
    @MockBean
    CustomUserDetailsService userDetailsService;

    //@WithMockUser(username = "test")
    @Test
    void getFlower() throws Exception {
        Flower flower = new Flower(1l, "testFlower", "red-rose.jpg", "test rose", null);
        when(flowerRepository.findById(anyLong())).thenReturn(java.util.Optional.of(flower));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/flowers/1");
        mvc.perform(requestBuilder)
                .andExpect(jsonPath("$.name").value("testFlower"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(status().isOk());
    }

}