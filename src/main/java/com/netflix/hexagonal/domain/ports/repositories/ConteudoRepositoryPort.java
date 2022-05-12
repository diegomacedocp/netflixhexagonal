package com.netflix.hexagonal.domain.ports.repositories;

import com.netflix.hexagonal.domain.dtos.ConteudoDTO;
import com.netflix.hexagonal.domain.models.Conteudo;
import com.netflix.hexagonal.infraestrutura.adapters.modelsDB.ConteudoDB;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConteudoRepositoryPort {

    List<Conteudo> buscarTodos();
    Conteudo buscarId(Long id);
    Conteudo salvar(Conteudo conteudo);
    List<Conteudo> obterConteudoPorRangeDeReleaseYear(Integer de, Integer ate);
    List<Conteudo> buscarPorTipo(String tipo);
    Page<Conteudo> buscarPorTituloPaginado(String titulo, int page, int size);
    void removerConteudoPorId(Long id);

}
