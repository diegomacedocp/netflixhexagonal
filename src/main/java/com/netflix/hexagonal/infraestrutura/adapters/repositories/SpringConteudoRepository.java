package com.netflix.hexagonal.infraestrutura.adapters.repositories;

import com.netflix.hexagonal.domain.models.Conteudo;
import com.netflix.hexagonal.infraestrutura.adapters.modelsDB.ConteudoDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringConteudoRepository extends MongoRepository<ConteudoDB, Long> {

    @Query("{ $and: [ { 'release_year': { $gt: ?0 } }, { 'release_year': { $lt: ?1 } } ] }")
    public List<ConteudoDB> obterConteudoPorRangeDeReleaseYear(Integer de, Integer ate);

    @Query("{ type: ?0 }")
    public List<ConteudoDB> buscarPorTipo(String tipo);

}