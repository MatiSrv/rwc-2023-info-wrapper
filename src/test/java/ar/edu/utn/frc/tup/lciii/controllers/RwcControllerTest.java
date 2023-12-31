package ar.edu.utn.frc.tup.lciii.controllers;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import ar.edu.utn.frc.tup.lciii.dtos.PoolDTO;
import ar.edu.utn.frc.tup.lciii.services.RwcService;

@ComponentScan(basePackages = "ar.edu.utn.frc.tup.lciii")
@WebMvcTest(RwcController.class)
public class RwcControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RwcService rwcService;

    List<PoolDTO> pools;
    PoolDTO pool;

    @BeforeEach
    void setUp() {
        pools = new ArrayList<>();
        pool = new PoolDTO("A",null);
        pools.add(pool);

    }

    @Test
    public void getPoolsTest() throws Exception {
        when(rwcService.getAllPools()).thenReturn(pools);

        mockMvc.perform(get("/rwc/2023/pools"))
                .andExpect(status().isOk());
    }

    @Test
    public void getPoolTest() throws Exception {
        when(rwcService.getPoolById("A")).thenReturn(pool);

        mockMvc.perform(get("/rwc/2023/pools/A"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pool_Id").value("A"))
            ;

    }
}
