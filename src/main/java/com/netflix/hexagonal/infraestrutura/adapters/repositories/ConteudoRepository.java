package com.netflix.hexagonal.infraestrutura.adapters.repositories;

import com.netflix.hexagonal.domain.models.Conteudo;
import com.netflix.hexagonal.domain.ports.repositories.ConteudoRepositoryPort;
import com.netflix.hexagonal.infraestrutura.adapters.modelsDB.ConteudoDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Profile("prod")
public class ConteudoRepository implements ConteudoRepositoryPort {

    @Autowired
    private SpringConteudoRepository springConteudoRepository;

    @Override
    public List<Conteudo> buscarTodos() {
        List<ConteudoDB> conteudoEntities = this.springConteudoRepository.findAll();
        return conteudoEntities.stream().map(ConteudoDB::toConteudo).collect(Collectors.toList());
    }

    @Override
    public Conteudo buscarId(Long id) {
        Optional<ConteudoDB> conteudoEntity = this.springConteudoRepository.findById(id);

        if (conteudoEntity.isPresent())
            return conteudoEntity.get().toConteudo();

        return null;
    }

    @Override
    public Conteudo salvar(Conteudo conteudo) {
        ConteudoDB conteudoDB;
        if (Objects.isNull(conteudo.getId())) {
            conteudoDB = new ConteudoDB(conteudo);

            long unixTime = System.currentTimeMillis() / 1000L;
            conteudoDB.set_id(unixTime);
        } else {
            conteudoDB = this.springConteudoRepository.findById(conteudo.getId()).get();
            conteudoDB.atualizar(conteudo);
        }

        return this.springConteudoRepository.save(conteudoDB).toConteudo();
    }

    @Override
    public List<Conteudo> obterConteudoPorRangeDeReleaseYear(Integer de, Integer ate) {
        List<ConteudoDB> conteudoEntities = this.springConteudoRepository.obterConteudoPorRangeDeReleaseYear(de, ate);
        return conteudoEntities.stream().map(ConteudoDB::toConteudo).collect(Collectors.toList());
    }

    @Override
    public List<Conteudo> buscarPorTipo(String tipo) {
        List<ConteudoDB> conteudoEntities = this.springConteudoRepository.findByType(tipo);
        return conteudoEntities.stream().map(ConteudoDB::toConteudo).collect(Collectors.toList());
    }

    @Override
    public Page<Conteudo> buscarPorTituloPaginado(String title, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("title").ascending());
        List<Conteudo> allConteudos = this.springConteudoRepository.findByTitlePage(title,pageable)
                .stream().map(ConteudoDB::toConteudo).collect(Collectors.toList());

        return new PageImpl<Conteudo>(allConteudos);
    }

    @Override
    public void removerConteudoPorId(Long id){
        this.springConteudoRepository.deleteById(id);
    }

}