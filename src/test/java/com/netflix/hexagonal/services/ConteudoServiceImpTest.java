package com.netflix.hexagonal.services;

import com.netflix.hexagonal.dominio.adaptadores.exception.ValidationException;
import com.netflix.hexagonal.dominio.dtos.ConteudoDTO;
import com.netflix.hexagonal.dominio.models.Conteudo;
import com.netflix.hexagonal.dominio.portas.intefaces.ConteudoServicePort;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ConteudoServiceImpTest {

    @Autowired
    private ConteudoServicePort conteudoServicePort;

    @Test
    public void testeBuscarConteudosSucesso() {
        List<ConteudoDTO> conteudosRetorno = conteudoServicePort.buscarConteudos();

        Assertions.assertNotNull(conteudosRetorno);
        Assertions.assertEquals(conteudosRetorno.get(0).getTipo(),"Tipo1");
        Assertions.assertEquals(conteudosRetorno.get(0).getTitulo(),"Titulo1");
        Assertions.assertEquals(conteudosRetorno.get(1).getTitulo(),"Titulo2");
        Assertions.assertEquals(conteudosRetorno.get(2).getTipo(),"Tipo3");
        Assertions.assertEquals(conteudosRetorno.get(2).getTitulo(),"Titulo3");

        ;
    }

    @Test
    public void testeErroCriarConteudoSemConteudo() {
        try {
            Conteudo conteudoRetorno = conteudoServicePort.criarConteudo(null);
        } catch (ValidationException e) {
            Assertions.assertEquals(e.getMessage(), "Deve-se passar um conteúdo para salvar!");
        }
    }

    @Test
    public void testeErroCriarConteudoSemTipoInformado() {
        Conteudo conteudoSalvar = new Conteudo();

        try {
            Conteudo conteudoRetorno = conteudoServicePort.criarConteudo(conteudoSalvar.toConteudoDTO());
        } catch (ValidationException e) {
            Assertions.assertEquals(e.getMessage(), "O tipo deve ser informado!");
        }
    }

    @Test
    public void testeErroCriarConteudoSemTituloInformado() {
        Conteudo conteudoSalvar = new Conteudo(null,"Tipo1",null,null,
                null,null,null,null,null,null,
                null,null);

        try {
            Conteudo conteudoRetorno = conteudoServicePort.criarConteudo(conteudoSalvar.toConteudoDTO());
        } catch (ValidationException e) {
            Assertions.assertEquals(e.getMessage(), "O titulo deve ser informado!");
        }
    }

    @Test
    public void testeErroCriarConteudoSemDiretoresInformado() {
        Conteudo conteudoSalvar = new Conteudo(null,"Tipo1","Titulo1",null,
                null,null,null,null,null,null,
                null,null);

        try {
            Conteudo conteudoRetorno = conteudoServicePort.criarConteudo(conteudoSalvar.toConteudoDTO());
        } catch (ValidationException e) {
            Assertions.assertEquals(e.getMessage(), "Os Diretores devem ser informado!");
        }
    }

    @Test
    public void testeErroCriarConteudoSemElencoInformado() {
        Conteudo conteudoSalvar = new Conteudo(null,"Tipo1","Titulo1",Arrays.asList("Diretor 1.1", "Diretor 1.2", "Diretor 1.3"),
                null,null,null,null,null,null,
                null,null);

        try {
            Conteudo conteudoRetorno = conteudoServicePort.criarConteudo(conteudoSalvar.toConteudoDTO());
        } catch (ValidationException e) {
            Assertions.assertEquals(e.getMessage(), "O Elenco deve ser informado!");
        }
    }

    @Test
    public void testeErroCriarConteudoSemPaisesInformado() {
        Conteudo conteudoSalvar = new Conteudo(null,"Tipo1","Titulo1",Arrays.asList("Diretor 1.1", "Diretor 1.2", "Diretor 1.3"),
                Arrays.asList("Elenco 1.1", "Elenco 1.2", "Elenco 1.3"),null,null,null,null,null,
                null,null);

        try {
            Conteudo conteudoRetorno = conteudoServicePort.criarConteudo(conteudoSalvar.toConteudoDTO());
        } catch (ValidationException e) {
            Assertions.assertEquals(e.getMessage(), "Os paises devem ser informados!");
        }
    }

    @Test
    public void testeErroCriarConteudoSemDataCadstroInformado() {
        Conteudo conteudoSalvar = new Conteudo(null,"Tipo1","Titulo1",Arrays.asList("Diretor 1.1", "Diretor 1.2", "Diretor 1.3"),
                Arrays.asList("Elenco 1.1", "Elenco 1.2", "Elenco 1.3"),Arrays.asList("Paises 1.1", "Paises 1.2", "Paises 1.3"),null,null,null,null,
                null,null);

        try {
            Conteudo conteudoRetorno = conteudoServicePort.criarConteudo(conteudoSalvar.toConteudoDTO());
        } catch (ValidationException e) {
            Assertions.assertEquals(e.getMessage(), "A data de cadastro deve ser informada!");
        }
    }

    @Test
    public void testeErroCriarConteudoSemAnoLancametoInformado() {
        Conteudo conteudoSalvar = new Conteudo(null,"Tipo1","Titulo1",Arrays.asList("Diretor 1.1", "Diretor 1.2", "Diretor 1.3"),
                Arrays.asList("Elenco 1.1", "Elenco 1.2", "Elenco 1.3"),Arrays.asList("Paises 1.1", "Paises 1.2", "Paises 1.3"),
                "Sun Sep 08 21:00:00 BRT 2001",null,null,null,
                null,null);

        try {
            Conteudo conteudoRetorno = conteudoServicePort.criarConteudo(conteudoSalvar.toConteudoDTO());
        } catch (ValidationException e) {
            Assertions.assertEquals(e.getMessage(), "O ano de lançamento deve ser informado!");
        }
    }

    @Test
    public void testeErroCriarConteudoSemDuracaoInformado() {
        Conteudo conteudoSalvar = new Conteudo(null,"Tipo1","Titulo1",Arrays.asList("Diretor 1.1", "Diretor 1.2", "Diretor 1.3"),
                Arrays.asList("Elenco 1.1", "Elenco 1.2", "Elenco 1.3"),Arrays.asList("Paises 1.1", "Paises 1.2", "Paises 1.3"),
                "Sun Sep 08 21:00:00 BRT 2001",2001,null,null,
                null,null);

        try {
            Conteudo conteudoRetorno = conteudoServicePort.criarConteudo(conteudoSalvar.toConteudoDTO());
        } catch (ValidationException e) {
            Assertions.assertEquals(e.getMessage(), "A duração deve ser informada!");
        }
    }

    @Test
    public void testeErroCriarConteudoSemDescricaoInformado() {
        Conteudo conteudoSalvar = new Conteudo(null,"Tipo1","Titulo1",Arrays.asList("Diretor 1.1", "Diretor 1.2", "Diretor 1.3"),
                Arrays.asList("Elenco 1.1", "Elenco 1.2", "Elenco 1.3"),Arrays.asList("Paises 1.1", "Paises 1.2", "Paises 1.3"),
                "Sun Sep 08 21:00:00 BRT 2001",2001,null,"1 min",
                null,null);

        try {
            Conteudo conteudoRetorno = conteudoServicePort.criarConteudo(conteudoSalvar.toConteudoDTO());
        } catch (ValidationException e) {
            Assertions.assertEquals(e.getMessage(), "A descrição deve ser informada!");
        }
    }

    @Test
    public void testeCriarConteudoComSucesso() {
        Conteudo conteudoSalvar = new Conteudo(null,"Tipo1","Titulo1",Arrays.asList("Diretor 1.1", "Diretor 1.2", "Diretor 1.3"),
                Arrays.asList("Elenco 1.1", "Elenco 1.2", "Elenco 1.3"),Arrays.asList("Paises 1.1", "Paises 1.2", "Paises 1.3"),
                "Sun Sep 08 21:00:00 BRT 2001",2001,null,"1 min",
                null,"Descricao 1");

        try {
            Conteudo conteudoRetorno = conteudoServicePort.criarConteudo(conteudoSalvar.toConteudoDTO());
            List<ConteudoDTO> conteudosRetorno = conteudoServicePort.buscarConteudos();

            Assertions.assertNotNull(conteudoRetorno);
            Assertions.assertEquals(conteudoRetorno.getId(),4L);
            Assertions.assertEquals(conteudoRetorno.getTipo(),"Tipo1");
            Assertions.assertEquals(conteudoRetorno.getTitulo(),"Titulo1");


        } catch (ValidationException e) {
            Assertions.assertEquals(e.getMessage(), "Não deve dar erro aqui!");
        }
    }

    @Test
    public void testeObterConteudoPorRangeDeReleaseYearSucesso() {
        List<ConteudoDTO> conteudosRetorno = null;
        try {
            conteudosRetorno = conteudoServicePort.obterConteudoPorRangeDeReleaseYear(2000,2005);
        } catch (ValidationException e) {
            Assertions.assertEquals(e.getMessage(),"Não deve entrar aqui!");
        }
        Assertions.assertTrue(!conteudosRetorno.isEmpty());
        Assertions.assertEquals(conteudosRetorno.get(0).getTipo(),"Tipo1");
        Assertions.assertEquals(conteudosRetorno.get(0).getTitulo(),"Titulo1");
    }

    @Test
    public void testeErroObterConteudoPorRangeDeReleaseYear() {
        List<ConteudoDTO> conteudosRetorno = null;
        try {
            conteudosRetorno = conteudoServicePort.obterConteudoPorRangeDeReleaseYear(3000,3005);
        } catch (ValidationException e) {
            Assertions.assertEquals(e.getMessage(),"Nenhum conteúdo encontrado!");
        }
    }

    @Test
    public void testeBuscarPorTipoSucesso() {
        try {
            List<ConteudoDTO> conteudosRetorno = conteudoServicePort.buscarPorTipo("Tipo1");

            Assertions.assertTrue(!conteudosRetorno.isEmpty());
            Assertions.assertEquals(conteudosRetorno.get(0).getTipo(),"Tipo1");
            Assertions.assertEquals(conteudosRetorno.get(0).getTitulo(),"Titulo1");
        } catch (ValidationException e) {
            Assertions.assertEquals(e.getMessage(),"Não deve entrar aqui!");
        }
    }

    @Test
    public void testeBuscarIdSucesso() {
        try {
            ConteudoDTO ConteudoDTORetorno = conteudoServicePort.buscarId(3L);

            Assertions.assertTrue(ConteudoDTORetorno != null);
            Assertions.assertEquals(ConteudoDTORetorno.getTipo(),"Tipo3");
            Assertions.assertEquals(ConteudoDTORetorno.getTitulo(),"Titulo3");
        } catch (ValidationException e) {
            Assertions.assertEquals(e.getMessage(),"Não deve entrar aqui!");
        }
    }

    @Test
    public void testeErroBuscarId() {
        try {
            ConteudoDTO ConteudoDTO = conteudoServicePort.buscarId(5L);
        } catch (ValidationException e) {
            Assertions.assertEquals(e.getMessage(),"Nenhum conteúdo encontrado!");
        }
    }
}
