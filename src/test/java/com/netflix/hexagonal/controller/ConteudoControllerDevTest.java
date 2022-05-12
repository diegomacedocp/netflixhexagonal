package com.netflix.hexagonal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hexagonal.domain.exception.business.ValidationException;
import com.netflix.hexagonal.domain.models.Conteudo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[0].tipo").value("Tipo1"))
                .andExpect(jsonPath("$[0].titulo").value("Titulo1"));

    }

    @Test
    public void testeBuscarId() throws Exception {
        mockMvc.perform(get("/conteudos/6"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors[*].message").value("Nenhum conteúdo encontrado!"));
    }

    @Test
    public void testeObterConteudoPorRangeDeReleaseYear() throws Exception {
        mockMvc.perform(get("/conteudos/range")
                        .queryParam("de", "2000")
                        .queryParam("ate", "2004"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[0].tipo").value("Tipo1"))
                .andExpect(jsonPath("$[0].titulo").value("Titulo1"));
    }

    @Test
    public void testeBuscarPorTipo() throws Exception {
        mockMvc.perform(get("/conteudos/tipo")
                        .queryParam("tipo", "Tipo2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[0].tipo").value("Tipo2"))
                .andExpect(jsonPath("$[0].titulo").value("Titulo2"));
    }


    @Test
    public void testeCriarConteudoSucesso() throws Exception {

        Conteudo conteudoSalvar = new Conteudo(null, "Tipo1", "Titulo1", Arrays.asList("Diretor 1.1", "Diretor 1.2", "Diretor 1.3"),
                Arrays.asList("Elenco 1.1", "Elenco 1.2", "Elenco 1.3"), Arrays.asList("Paises 1.1", "Paises 1.2", "Paises 1.3"),
                "Sun Sep 08 21:00:00 BRT 2001", 2001, null, "1 min",
                null, "Descricao 1", null);

        mockMvc.perform(post("/conteudos")
                        .content(asJsonString(conteudoSalvar))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.tipo").value("Tipo1"))
                .andExpect(jsonPath("$.titulo").value("Titulo1"));
    }

    @Test
    public void testeErroCriarConteudo() throws Exception {

        Conteudo conteudoSalvar = new Conteudo(null, null, null, null,
                null, null, null, null, null, null,
                null, null, null);

        mockMvc.perform(post("/conteudos")
                        .content(asJsonString(conteudoSalvar))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors[0].message").value("O campo é obrigatório o preenchimento"))
                .andExpect(jsonPath("$.errors[1].message").value("O campo é obrigatório o preenchimento"))
                .andExpect(jsonPath("$.errors[2].message").value("O campo é obrigatório o preenchimento"))
                .andExpect(jsonPath("$.errors[3].message").value("O campo é obrigatório o preenchimento"))
                .andExpect(jsonPath("$.errors[4].message").value("O campo é obrigatório o preenchimento"))
                .andExpect(jsonPath("$.errors[5].message").value("O campo é obrigatório o preenchimento"))
                .andExpect(jsonPath("$.errors[6].message").value("O campo é obrigatório o preenchimento"))
                .andExpect(jsonPath("$.errors[7].message").value("O campo é obrigatório o preenchimento"))
                .andExpect(jsonPath("$.errors[8].message").value("O campo é obrigatório o preenchimento"));
    }

    @Test
    public void testeRemoverConteudoPorId() throws Exception {
        mockMvc.perform(delete("/conteudos/remover/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testeErroRemoverConteudoPorId() throws Exception {
        mockMvc.perform(delete("/conteudos/remover/6"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors[*].message").value("O conteúdo não foi encontrado para remover!"));
    }

    @Test
    public void testeAtualizarImagem() throws Exception {

        FileInputStream fis = new FileInputStream("C:\\image.jpg");
        MockMultipartFile image = new MockMultipartFile("image", "", "image/jpeg", fis);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/conteudos/image/atualizar/1")
                        .file(image))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testeErroAtualizarImagem() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.multipart("/conteudos/image/atualizar/1"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors[*].message").value("Required request part 'image' is not present"));

        FileInputStream fis = new FileInputStream("C:\\image.jpg");
        MockMultipartFile image = new MockMultipartFile("image", "", "image/png", fis);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/conteudos/image/atualizar/1")
                        .file(image))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors[*].message").value("Arquivo inválido. Aceita somente .jpg"));

    }

    @Test
    public void testeRemoverImagem() throws Exception {
        mockMvc.perform(delete("/conteudos/image/remover/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testeErroRemoverImagem() throws Exception {
        mockMvc.perform(delete("/conteudos/image/remover/6"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors[*].message").value("Nenhum conteúdo encontrado!"));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
