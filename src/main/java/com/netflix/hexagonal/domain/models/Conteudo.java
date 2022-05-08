package com.netflix.hexagonal.domain.models;

import com.netflix.hexagonal.domain.dtos.ConteudoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Conteudo {

    private Long id;
    private String tipo;
    private String titulo;
    private List<String> diretores;
    private List<String> elenco;
    private List<String> paises;
    private String dataCadastro;
    private Integer anoLancamento;
    private String avaliacao;
    private String duracao;
    private List<String> listadoEm;
    private String descricao;

    public Conteudo() {

    }

    public Conteudo(ConteudoDTO conteudoDTO) {
        this.tipo = conteudoDTO.getTipo();
        this.titulo = conteudoDTO.getTitulo();
        this.diretores = conteudoDTO.getDiretores();
        this.elenco = conteudoDTO.getElenco();
        this.paises = conteudoDTO.getPaises();
        this.dataCadastro = conteudoDTO.getDataCadastro();
        this.anoLancamento = conteudoDTO.getAnoLancamento();
        this.avaliacao = conteudoDTO.getAvaliacao();
        this.duracao = conteudoDTO.getDuracao();
        this.listadoEm = conteudoDTO.getListadoEm();
        this.descricao = conteudoDTO.getDescricao();
    }

    public ConteudoDTO toConteudoDTO() {
        return new ConteudoDTO(this.tipo,this.titulo,this.diretores,this.elenco,this.paises,this.dataCadastro,
            this.anoLancamento,this.avaliacao,this.duracao,this.listadoEm,this.descricao);

    }

}
