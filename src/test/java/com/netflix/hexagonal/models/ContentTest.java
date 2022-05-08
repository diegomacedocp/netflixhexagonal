package com.netflix.hexagonal.models;

import com.netflix.hexagonal.domain.dtos.ConteudoDTO;
import com.netflix.hexagonal.domain.models.Conteudo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ContentTest {

    @Test
    void testingClassModelContent() {

        Conteudo conteudo = new Conteudo(Long.valueOf(1L), "Tipo1", "Titulo1",
                Arrays.asList("Diretor 1.1", "Diretor 1.2", "Diretor 1.3"), Arrays.asList("Elenco 1.1", "Elenco 1.2", "Elenco 1.3"),
                Arrays.asList("Paises 1.1", "Paises 1.2", "Paises 1.3"), "Sun Sep 08 21:00:00 BRT 2001",
                2001, "Avaliacao 1", "1 min",
                Arrays.asList("Listado 1.1", "Listado 1.2", "Listado 1.3"), "Descricao 1");

        assertEquals("Tipo1", conteudo.getTipo());
        assertEquals("Titulo1", conteudo.getTitulo());

        ConteudoDTO conteudoDTO = conteudo.toConteudoDTO();

        assertEquals(conteudo.getTipo(), conteudoDTO.getTipo());
        assertEquals(conteudo.getTitulo(), conteudoDTO.getTitulo());

    }

}