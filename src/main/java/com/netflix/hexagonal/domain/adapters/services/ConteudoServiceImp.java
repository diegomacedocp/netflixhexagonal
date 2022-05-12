package com.netflix.hexagonal.domain.adapters.services;

import com.netflix.hexagonal.domain.exception.business.ValidationException;
import com.netflix.hexagonal.domain.dtos.ConteudoDTO;
import com.netflix.hexagonal.domain.models.Conteudo;
import com.netflix.hexagonal.domain.ports.intefaces.ConteudoServicePort;
import com.netflix.hexagonal.domain.ports.repositories.ConteudoRepositoryPort;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
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
    public Conteudo criarConteudo(Conteudo conteudo) throws ValidationException {
        if(Objects.isNull(conteudo))
            throw new ValidationException("Deve-se passar um conteúdo para salvar!");

        if(Objects.isNull(conteudo.getTipo()) || conteudo.getTipo().isEmpty())
            throw new ValidationException("O tipo deve ser informado!");

        if(Objects.isNull(conteudo.getTitulo()) || conteudo.getTitulo().isEmpty())
            throw new ValidationException("O titulo deve ser informado!");

        if(Objects.isNull(conteudo.getDiretores()) || conteudo.getDiretores().isEmpty())
            throw new ValidationException("Os Diretores devem ser informado!");

        if(Objects.isNull(conteudo.getElenco()) || conteudo.getElenco().isEmpty())
            throw new ValidationException("O Elenco deve ser informado!");

        if(Objects.isNull(conteudo.getPaises()) || conteudo.getPaises().isEmpty())
            throw new ValidationException("Os paises devem ser informados!");

        if(Objects.isNull(conteudo.getDataCadastro()) || conteudo.getDataCadastro().isEmpty())
            throw new ValidationException("A data de cadastro deve ser informada!");

        if(Objects.isNull(conteudo.getAnoLancamento()) || conteudo.getAnoLancamento() == 0)
            throw new ValidationException("O ano de lançamento deve ser informado!");

        if(Objects.isNull(conteudo.getDuracao()) || conteudo.getDuracao().isEmpty())
            throw new ValidationException("A duração deve ser informada!");

        if(Objects.isNull(conteudo.getDescricao()) || conteudo.getDescricao().isEmpty())
            throw new ValidationException("A descrição deve ser informada!");

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

    @Override
    public Page<ConteudoDTO> buscarPorTituloPaginado(String titulo, int page, int size) {
        List<ConteudoDTO> allCollect = this.conteudoRepository.buscarPorTituloPaginado(titulo, page, size)
                .stream().map(Conteudo::toConteudoDTO).collect(Collectors.toList());

        return new PageImpl<ConteudoDTO>(allCollect);
    }

    @Override
    public void removerConteudoPorId(Long id) throws ValidationException {

        Conteudo conteudo = this.conteudoRepository.buscarId(id);

        if(Objects.isNull(conteudo))
            throw new  ValidationException("O conteúdo não foi encontrado para remover!");

        this.conteudoRepository.removerConteudoPorId(id);
    }

    @Override
    public ConteudoDTO atualizarImagem(Long id, MultipartFile file) throws ValidationException {
        Conteudo conteudo = this.conteudoRepository.buscarId(id);

        if(Objects.isNull(conteudo))
            throw new  ValidationException("Nenhum conteúdo encontrado!");

        if(Objects.isNull(file.getContentType()) || !file.getContentType().equals("image/jpeg"))
            throw new  ValidationException("Arquivo inválido. Aceita somente .jpg");

        BASE64Encoder encoder = new BASE64Encoder();

        try {
            String imageBase64 = new String(encoder.encode(file.getBytes()));
            conteudo.setImage(imageBase64);
        } catch (IOException e) {
            throw new  ValidationException("Erro ao converter imagem em Base64.");
        }

        return this.conteudoRepository.salvar(conteudo).toConteudoDTO();
    }

    @Override
    public void removerImagem(Long id) throws ValidationException {
        Conteudo conteudo = this.conteudoRepository.buscarId(id);

        if(Objects.isNull(conteudo))
            throw new  ValidationException("Nenhum conteúdo encontrado!");

        conteudo.setImage(null);
        this.conteudoRepository.salvar(conteudo).toConteudoDTO();
    }
}
