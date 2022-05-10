package com.netflix.hexagonal.infraestrutura.adapters.repositories;

import com.netflix.hexagonal.domain.models.Conteudo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringConteudoRepository extends MongoRepository<Conteudo, Long> {

    @Query("{ $and: [ { 'release_year': { $gt: ?0 } }, { 'release_year': { $lt: ?1 } } ] }")
    List<Conteudo> obterConteudoPorRangeDeReleaseYear(Integer de, Integer ate);

    @Query("{ type: ?0 }")
    List<Conteudo> buscarPorTipo(String tipo);

}

