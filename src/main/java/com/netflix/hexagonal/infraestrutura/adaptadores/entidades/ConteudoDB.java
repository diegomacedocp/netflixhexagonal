package com.netflix.hexagonal.infraestrutura.adaptadores.entidades;

import com.netflix.hexagonal.dominio.models.Conteudo;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;

@Data
@Document("netflix")
public class ConteudoDB {
    @Id
    Long _id;
    String type;
    String title;
    String date_added;
    Integer release_year;
    String rating;
    String duration;
    String description;
    List<String> cast;
    List<String> countries;
    List<String> directors;
    List<String> listed_in;

    public ConteudoDB() {
    }

    public ConteudoDB(Conteudo conteudo) {
        this._id = conteudo.getId();
        this.type = conteudo.getTipo();
        this.title = conteudo.getTitulo();
        this.directors = conteudo.getDiretores();
        this.cast = conteudo.getElenco();
        this.countries = conteudo.getPaises();
        this.date_added = conteudo.getDataCadastro();
        this.release_year = conteudo.getAnoLancamento();
        this.rating = conteudo.getAvaliacao();
        this.duration = conteudo.getDuracao();
        this.listed_in = conteudo.getListadoEm();
        this.description = conteudo.getDescricao();
    }

    public void atualizar(Conteudo conteudo) {
        this.type = conteudo.getTipo();
        this.title = conteudo.getTitulo();
        this.directors = conteudo.getDiretores();
        this.cast = conteudo.getElenco();
        this.countries = conteudo.getPaises();
        this.date_added = conteudo.getDataCadastro();
        this.release_year = conteudo.getAnoLancamento();
        this.rating = conteudo.getAvaliacao();
        this.duration = conteudo.getDuracao();
        this.listed_in = conteudo.getListadoEm();
        this.description = conteudo.getDescricao();
    }


    public Conteudo toConteudo() {
        return new Conteudo(this._id,this.type,this.title,this.directors,this.cast,this.countries,this.date_added,
                this.release_year,this.rating,this.duration, this.listed_in,this.description);

    }
}
