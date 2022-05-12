package com.netflix.hexagonal.infraestrutura.adapters.repositories;

import com.netflix.hexagonal.domain.models.Conteudo;
import com.netflix.hexagonal.infraestrutura.adapters.modelsDB.ConteudoDB;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringConteudoRepository extends MongoRepository<ConteudoDB, Long> {
    @Query("{ $and: [ { 'release_year': { $gt: ?0 } }, { 'release_year': { $lt: ?1 } } ] }")
    List<ConteudoDB> obterConteudoPorRangeDeReleaseYear(Integer de, Integer ate);
    @Query("{ title: /^?0/i  }")
    Page<ConteudoDB> findByTitlePage(@Param("title") String title, Pageable pageable);
    List<ConteudoDB> findByType(String tipo);

}