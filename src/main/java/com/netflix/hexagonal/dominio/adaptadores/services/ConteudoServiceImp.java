package com.netflix.hexagonal.dominio.adaptadores.services;

import com.netflix.hexagonal.dominio.adaptadores.exception.ValidationException;
import com.netflix.hexagonal.dominio.dtos.ConteudoDTO;
import com.netflix.hexagonal.dominio.models.Conteudo;
import com.netflix.hexagonal.dominio.portas.intefaces.ConteudoServicePort;
import com.netflix.hexagonal.dominio.portas.repositories.ConteudoRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ConteudoServiceImp implements ConteudoServicePort {

    @Autowired
    private ConteudoRepositoryPort conteudoRepository;

    @Override
    public List<ConteudoDTO> buscarConteudos() {
        List<Conteudo> conteudos = this.conteudoRepository.buscarTodos();

        if(Objects.isNull(conteudos))
            return new ArrayList<>();

        return conteudos.stream().map(Conteudo::toConteudoDTO).collect(Collectors.toList());
    }

    @Override
    public Conteudo criarConteudo(ConteudoDTO conteudoDTO) throws ValidationException {
        if(Objects.isNull(conteudoDTO))
            throw new ValidationException("Deve-se passar um conteúdo para salvar!");

        if(Objects.isNull(conteudoDTO.getTipo()) || conteudoDTO.getTipo().isEmpty())
            throw new ValidationException("O tipo deve ser informado!");

        if(Objects.isNull(conteudoDTO.getTitulo()) || conteudoDTO.getTitulo().isEmpty())
            throw new ValidationException("O titulo deve ser informado!");

        if(Objects.isNull(conteudoDTO.getDiretores()) || conteudoDTO.getDiretores().isEmpty())
            throw new ValidationException("Os Diretores devem ser informado!");

        if(Objects.isNull(conteudoDTO.getElenco()) || conteudoDTO.getElenco().isEmpty())
            throw new ValidationException("O Elenco deve ser informado!");

        if(Objects.isNull(conteudoDTO.getPaises()) || conteudoDTO.getPaises().isEmpty())
            throw new ValidationException("Os paises devem ser informados!");

        if(Objects.isNull(conteudoDTO.getDataCadastro()) || conteudoDTO.getDataCadastro().isEmpty())
            throw new ValidationException("A data de cadastro deve ser informada!");

        if(Objects.isNull(conteudoDTO.getAnoLancamento()) || conteudoDTO.getAnoLancamento() == 0)
            throw new ValidationException("O ano de lançamento deve ser informado!");

        if(Objects.isNull(conteudoDTO.getDuracao()) || conteudoDTO.getDuracao().isEmpty())
            throw new ValidationException("A duração deve ser informada!");

        if(Objects.isNull(conteudoDTO.getDescricao()) || conteudoDTO.getDescricao().isEmpty())
            throw new ValidationException("A descrição deve ser informada!");

        Conteudo conteudo = new Conteudo(conteudoDTO);
        return this.conteudoRepository.salvar(conteudo);
    }

    @Override
    public List<ConteudoDTO> obterConteudoPorRangeDeReleaseYear(Integer de, Integer ate) throws ValidationException {
        List<Conteudo> conteudos = this.conteudoRepository.obterConteudoPorRangeDeReleaseYear(de,ate);

        if(Objects.isNull(conteudos))
            throw new  ValidationException("Nenhum conteúdo encontrado!");

        return conteudos.stream().map(Conteudo::toConteudoDTO).collect(Collectors.toList());
    }

    @Override
    public List<ConteudoDTO> buscarPorTipo(String tipo) throws ValidationException {
        List<Conteudo> conteudos = this.conteudoRepository.buscarPorTipo(tipo);

        if(Objects.isNull(conteudos))
            throw new  ValidationException("Nenhum conteúdo encontrado!");

        return conteudos.stream().map(Conteudo::toConteudoDTO).collect(Collectors.toList());
    }

    @Override
    public ConteudoDTO buscarId(Long id) throws ValidationException {
        Conteudo conteudo = this.conteudoRepository.buscarId(id);

        if(Objects.isNull(conteudo))
            throw new  ValidationException("Nenhum conteúdo encontrado!");

        return conteudo.toConteudoDTO();
    }
}
