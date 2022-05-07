package com.netflix.hexagonal.dominio.portas.intefaces;

import com.netflix.hexagonal.dominio.adaptadores.exception.ValidationException;
import com.netflix.hexagonal.dominio.dtos.ConteudoDTO;
import com.netflix.hexagonal.dominio.models.Conteudo;

import java.util.List;

public interface ConteudoServicePort {
    List<ConteudoDTO> buscarConteudos();
    Conteudo criarConteudo(ConteudoDTO conteudoDTO) throws ValidationException;
    List<ConteudoDTO> obterConteudoPorRangeDeReleaseYear(Integer de, Integer ate) throws ValidationException;
    List<ConteudoDTO> buscarPorTipo(String tipo) throws ValidationException;
    ConteudoDTO buscarId(Long id) throws ValidationException;
}
