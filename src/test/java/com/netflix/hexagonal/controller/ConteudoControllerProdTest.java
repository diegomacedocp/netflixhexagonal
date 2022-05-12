package com.netflix.hexagonal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hexagonal.domain.models.Conteudo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.FileInputStream;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("prod")
public class ConteudoControllerProdTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveRetornarTodosConteudos() throws Exception {
        mockMvc.perform(get("/conteudos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]").exists());
    }

    @Test
    public void testeBuscarId() throws Exception {
        mockMvc.perform(get("/conteudos/81145628"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.tipo").value("Movie"))
                .andExpect(jsonPath("$.titulo").value("Norm of the North: King Sized Adventure"))
                .andExpect(jsonPath("$.anoLancamento").value(2019));
    }

    @Test
    public void testeErroBuscarId() throws Exception {
        mockMvc.perform(get("/conteudos/6"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors[*].message").value("Nenhum conteúdo encontrado!"));
    }

    @Test
    public void testeObterConteudoPorRangeDeReleaseYear() throws Exception {
        mockMvc.perform(get("/conteudos/range")
                        .queryParam("de", "2000")
                        .queryParam("ate", "2004"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]").exists())
                .andExpect(jsonPath("$[*]").isArray())
                .andExpect(jsonPath("$[*].tipo", containsInRelativeOrder("Movie", "TV Show")))
                .andExpect(jsonPath("$[*].titulo", containsInRelativeOrder("Bangkok Hell", "Friends")));
    }

    @Test
    public void testeBuscarPorTipo() throws Exception {
        mockMvc.perform(get("/conteudos/tipo")
                        .queryParam("tipo", "Movie"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]").exists())
                .andExpect(jsonPath("$[*]").isArray())
                .andExpect(jsonPath("$[*].tipo", containsInRelativeOrder("Movie")))
                .andExpect(jsonPath("$[*].tipo", not(containsInRelativeOrder("TV Show"))))
                .andExpect(jsonPath("$[*].titulo", containsInRelativeOrder("Manhattan Romance", "Kill Me If You Dare")));
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
    public void testeBuscarPorTituloPaginado() throws Exception {
        mockMvc.perform(get("/conteudos/titulo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[*].tipo", containsInRelativeOrder("TV Show","Movie")))
                .andExpect(jsonPath("$.content[*].titulo", containsInRelativeOrder("Carlos Ballarta: El amor es de putos", "Chappie")))
                .andExpect(jsonPath("$.size", is(1000)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(1000)));

        mockMvc.perform(get("/conteudos/titulo")
                        .queryParam("page", "0")
                        .queryParam("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[*].tipo", containsInRelativeOrder("Movie" )))
                .andExpect(jsonPath("$.content[*].titulo", containsInRelativeOrder("(T)ERROR")))
                .andExpect(jsonPath("$.size", is(10)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(10)));


        mockMvc.perform(get("/conteudos/titulo")
                        .queryParam("titulo", "Norm of")
                        .queryParam("page", "0")
                        .queryParam("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[*].tipo", containsInRelativeOrder("Movie")))
                .andExpect(jsonPath("$.content[*].titulo", containsInRelativeOrder("Norm of the North: Keys to the Kingdom","Norm of the North: King Sized Adventure")))
                .andExpect(jsonPath("$.size", is(2)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.totalElements", is(2)));

    }

    @Test
    public void testeAtualizarImagem() throws Exception {

        FileInputStream fis = new FileInputStream("C:\\image.jpg");
        MockMultipartFile image = new MockMultipartFile("image", "", "image/jpeg", fis);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/conteudos/image/atualizar/80117401")
                        .file(image))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
