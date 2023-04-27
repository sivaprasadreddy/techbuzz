package com.sivalabs.techbuzz;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

class TechBuzzApplicationTests extends AbstractIntegrationTest {

    @Test
    void contextLoads() throws Exception {
        mockMvc.perform(get("/actuator/health")).andExpect(status().isOk());
    }
}
