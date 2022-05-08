package com.netflix.hexagonal.infraestrutura.adapters.repositories;

import com.netflix.hexagonal.domain.models.Conteudo;
import com.netflix.hexagonal.domain.ports.repositories.ConteudoRepositoryPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Profile("dev")
public class ConteudoRepositoryFakeDb implements ConteudoRepositoryPort {

    private Map<Long, Conteudo> banco = new HashMap<>();

    public ConteudoRepositoryFakeDb() {

        Conteudo conteudo1 = new Conteudo(Long.valueOf(1L), "Tipo1", "Titulo1",
                Arrays.asList("Diretor 1.1", "Diretor 1.2", "Diretor 1.3"), Arrays.asList("Elenco 1.1", "Elenco 1.2", "Elenco 1.3"),
                Arrays.asList("Paises 1.1", "Paises 1.2", "Paises 1.3"), "Sun Sep 08 21:00:00 BRT 2001",
                2001, "Avaliacao 1", "1 min",
                Arrays.asList("Listado 1.1", "Listado 1.2", "Listado 1.3"), "Descricao 1");

        Conteudo conteudo2 = new Conteudo(Long.valueOf(2L), "Tipo2", "Titulo2",
                Arrays.asList("Diretor 2.1", "Diretor 2.2", "Diretor 2.3"), Arrays.asList("Elenco 2.1", "Elenco 2.2", "Elenco 2.3"),
                Arrays.asList("Paises 2.1", "Paises 2.2", "Paises 2.3"), "Sun Sep 08 21:00:00 BRT 2002",
                2002, "Avaliacao 2", "2 min",
                Arrays.asList("Listado 2.1", "Listado 2.2", "Listado 2.3"), "Descricao 2");

        Conteudo conteudo3 = new Conteudo(Long.valueOf(3L), "Tipo3", "Titulo3",
                Arrays.asList("Diretor 3.1", "Diretor 3.2", "Diretor 3.3"), Arrays.asList("Elenco 3.1", "Elenco 3.2", "Elenco 3.3"),
                Arrays.asList("Paises 3.1", "Paises 3.2", "Paises 3.3"), "Sun Sep 08 21:00:00 BRT 2003",
                2003, "Avaliacao 3", "3 min",
                Arrays.asList("Listado 3.1", "Listado 3.2", "Listado 3.3"), "Descricao 3");

        banco.put(1L, conteudo1);
        banco.put(2L, conteudo2);
        banco.put(3L, conteudo3);
    }

    @Override
    public List<Conteudo> buscarTodos() {
        System.out.println("Fake banco de dados -> Conteudo buscarTodos()");
        return new ArrayList<Conteudo>(banco.values());
    }

    @Override
    public Conteudo buscarId(Long id) {
        System.out.println("Fake banco de dados -> Conteudo buscarId(id)");
        return banco.get(id);
    }

    @Override
    public Conteudo salvar(Conteudo conteudo) {
        System.out.println("Fake banco de dados -> Conteudo salvar(conteudo)");
        Long keySalvar = Long.valueOf(banco.size()) + 1;
        conteudo.setId(keySalvar);
        banco.put(keySalvar,conteudo) ;
        return conteudo;
    }

    @Override
    public List<Conteudo> obterConteudoPorRangeDeReleaseYear(Integer de, Integer ate) {
        System.out.println("Fake banco de dados -> Conteudo obterConteudoPorRangeDeReleaseYear(de,ate)");
        List<Conteudo> lista = new ArrayList<Conteudo>(banco.values());
        return lista.stream().filter( l -> l.getAnoLancamento() > de && l.getAnoLancamento() < ate ).collect(Collectors.toList());
    }

    @Override
    public List<Conteudo> buscarPorTipo(String tipo) {
        System.out.println("Fake banco de dados -> Conteudo buscarPorTipo(tipo)");
        List<Conteudo> lista = new ArrayList<Conteudo>(banco.values());
        return lista.stream().filter( l -> l.getTipo().equals(tipo) ).collect(Collectors.toList());
    }
}
