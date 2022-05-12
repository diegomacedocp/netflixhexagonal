package com.netflix.hexagonal.domain.ports.intefaces;

import com.netflix.hexagonal.domain.exception.business.ValidationException;
import com.netflix.hexagonal.domain.dtos.ConteudoDTO;
import com.netflix.hexagonal.domain.models.Conteudo;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ConteudoServicePort {
    List<ConteudoDTO> buscarConteudos();
    Conteudo criarConteudo(Conteudo conteudo) throws ValidationException;
    List<ConteudoDTO> obterConteudoPorRangeDeReleaseYear(Integer de, Integer ate) throws ValidationException;
    List<ConteudoDTO> buscarPorTipo(String tipo) throws ValidationException;
    ConteudoDTO buscarId(Long id) throws ValidationException;
    Page<ConteudoDTO> buscarPorTituloPaginado(String titulo, int page, int size);
    void removerConteudoPorId(Long id) throws ValidationException;
    ConteudoDTO atualizarImagem(Long id, MultipartFile file) throws ValidationException;
    void removerImagem(Long id) throws ValidationException;
}
