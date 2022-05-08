package com.netflix.hexagonal.domain.ports.repositories;

import com.netflix.hexagonal.domain.models.Conteudo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConteudoRepositoryPort {

    List<Conteudo> buscarTodos();
    Conteudo buscarId(Long id);
    Conteudo salvar(Conteudo conteudo);
    public List<Conteudo> obterConteudoPorRangeDeReleaseYear(Integer de, Integer ate);
    public List<Conteudo> buscarPorTipo(String tipo);

}
