package com.netflix.hexagonal.infraestrutura.adapters.repositories;

import com.netflix.hexagonal.domain.models.Conteudo;
import com.netflix.hexagonal.domain.ports.repositories.ConteudoRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Profile("prod")
public class ConteudoRepository implements ConteudoRepositoryPort {

    @Autowired
    private SpringConteudoRepository springConteudoRepository;

    @Override
    public List<Conteudo> buscarTodos() {
        return this.springConteudoRepository.findAll();
    }

    @Override
    public Conteudo buscarId(Long id) {
        Optional<Conteudo> conteudoEntity = this.springConteudoRepository.findById(id);

        if (conteudoEntity.isPresent())
            return conteudoEntity.get();

        return null;
    }

    @Override
    public Conteudo salvar(Conteudo conteudo) {
        if (!Objects.isNull(conteudo.getId()))
            conteudo = this.springConteudoRepository.findById(conteudo.getId()).get();

        return this.springConteudoRepository.save(conteudo);
    }

    @Override
    public List<Conteudo> obterConteudoPorRangeDeReleaseYear(Integer de, Integer ate) {
        return this.springConteudoRepository.obterConteudoPorRangeDeReleaseYear(de,ate);
    }

    @Override
    public List<Conteudo> buscarPorTipo(String tipo) {
        return this.springConteudoRepository.buscarPorTipo(tipo);
    }
}
