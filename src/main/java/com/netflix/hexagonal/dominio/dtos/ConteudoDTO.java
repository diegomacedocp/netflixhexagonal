package com.netflix.hexagonal.dominio.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ConteudoDTO {

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

}
