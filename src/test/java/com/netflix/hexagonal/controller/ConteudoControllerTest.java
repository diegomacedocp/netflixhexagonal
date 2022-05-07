package com.netflix.hexagonal.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class ConteudoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveRetornarTodosConteudos() throws Exception {
        this.mockMvc.perform(get("/conteudos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Tipo1")));
    }

    @Test
    public void testeBuscarId() throws Exception {
        this.mockMvc.perform(get("/conteudos/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Titulo2")));
    }

    @Test
    public void testeObterConteudoPorRangeDeReleaseYear() throws Exception {
        this.mockMvc.perform(get("/conteudos/range")
                        .queryParam("de", "2000")
                        .queryParam("ate", "2004"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Descricao 3")));
    }

    @Test
    public void testeBuscarPorTipo() throws Exception {
        this.mockMvc.perform(get("/conteudos/tipo")
                        .queryParam("tipo", "Tipo2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Diretor 2.2")));
    }
}
