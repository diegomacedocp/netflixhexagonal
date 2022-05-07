package com.netflix.hexagonal.infraestrutura.adaptadores.repositories;

import com.netflix.hexagonal.infraestrutura.adaptadores.entidades.ConteudoDB;
import org.springframework.context.annotation.Profile;
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

