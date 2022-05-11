package com.netflix.hexagonal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hexagonal.domain.models.Conteudo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class ConteudoControllerDevTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveRetornarTodosConteudos() throws Exception {
        mockMvc.perform(get("/conteudos"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tipo").value("Tipo1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].titulo").value("Titulo1"));

    }

    @Test
    public void testeBuscarId() throws Exception {
        mockMvc.perform(get("/conteudos/6"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[*].message").value("Nenhum conteúdo encontrado!"));
    }

    @Test
    public void testeObterConteudoPorRangeDeReleaseYear() throws Exception {
        mockMvc.perform(get("/conteudos/range")
                        .queryParam("de", "2000")
                        .queryParam("ate", "2004"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tipo").value("Tipo1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].titulo").value("Titulo1"));
    }

    @Test
    public void testeBuscarPorTipo() throws Exception {
        mockMvc.perform(get("/conteudos/tipo")
                        .queryParam("tipo", "Tipo2"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].tipo").value("Tipo2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].titulo").value("Titulo2"));
    }


    @Test
    public void testeCriarConteudoSucesso() throws Exception {

        Conteudo conteudoSalvar = new Conteudo(null,"Tipo1","Titulo1", Arrays.asList("Diretor 1.1", "Diretor 1.2", "Diretor 1.3"),
                Arrays.asList("Elenco 1.1", "Elenco 1.2", "Elenco 1.3"),Arrays.asList("Paises 1.1", "Paises 1.2", "Paises 1.3"),
                "Sun Sep 08 21:00:00 BRT 2001",2001,null,"1 min",
                null,"Descricao 1",null);

        mockMvc.perform(post("/conteudos")
                        .content(asJsonString(conteudoSalvar))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipo").value("Tipo1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("Titulo1"));
    }

    @Test
    public void testeErroCriarConteudo() throws Exception {

        Conteudo conteudoSalvar = new Conteudo(null,null,null,null,
                null,null,null,null,null,null,
                null,null,null);

        mockMvc.perform(post("/conteudos")
                        .content(asJsonString(conteudoSalvar))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message").value("O campo é obrigatório o preenchimento"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[1].message").value("O campo é obrigatório o preenchimento"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[2].message").value("O campo é obrigatório o preenchimento"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[3].message").value("O campo é obrigatório o preenchimento"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[4].message").value("O campo é obrigatório o preenchimento"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[5].message").value("O campo é obrigatório o preenchimento"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[6].message").value("O campo é obrigatório o preenchimento"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[7].message").value("O campo é obrigatório o preenchimento"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[8].message").value("O campo é obrigatório o preenchimento"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
