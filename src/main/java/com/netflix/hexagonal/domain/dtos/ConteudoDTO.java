package com.netflix.hexagonal.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ConteudoDTO {

    @NotBlank(message = "{required.validation}")
    @Size(min = 4, max = 30, message = "{size.validation}")
    private String tipo;

    @NotBlank(message = "{required.validation}")
    private String titulo;

    @NotEmpty(message = "{required.validation}")
    private List<String> diretores;

    @NotEmpty(message = "{required.validation}")
    private List<String> elenco;

    @NotEmpty(message = "{required.validation}")
    private List<String> paises;

    @NotBlank(message = "{required.validation}")
    private String dataCadastro;

    @NotNull(message = "{required.validation}")
    @Range(min = 1900, max = 3000, message = "{range.ano.validation}")
    private Integer anoLancamento;
    private String avaliacao;

    @NotBlank(message = "{required.validation}")
    private String duracao;
    private List<String> listadoEm;

    @NotBlank(message = "{required.validation}")
    private String descricao;

}
