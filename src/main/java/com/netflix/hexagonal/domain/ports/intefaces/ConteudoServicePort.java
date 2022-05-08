package com.netflix.hexagonal.domain.ports.intefaces;

import com.netflix.hexagonal.domain.exception.business.ValidationException;
import com.netflix.hexagonal.domain.dtos.ConteudoDTO;
import com.netflix.hexagonal.domain.models.Conteudo;

import java.util.List;

public interface ConteudoServicePort {
    List<ConteudoDTO> buscarConteudos();
    Conteudo criarConteudo(ConteudoDTO conteudoDTO) throws ValidationException;
    List<ConteudoDTO> obterConteudoPorRangeDeReleaseYear(Integer de, Integer ate) throws ValidationException;
    List<ConteudoDTO> buscarPorTipo(String tipo) throws ValidationException;
    ConteudoDTO buscarId(Long id) throws ValidationException;
}
