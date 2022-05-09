package com.netflix.hexagonal.adapters.controllers;

import com.netflix.hexagonal.domain.exception.business.ValidationException;
import com.netflix.hexagonal.domain.dtos.ConteudoDTO;
import com.netflix.hexagonal.domain.models.Conteudo;
import com.netflix.hexagonal.domain.ports.intefaces.ConteudoServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/conteudos")

public class ConteudoController {
    @Autowired
    private ConteudoServicePort conteudoServicePort;

    @PostMapping
    public ResponseEntity<Conteudo> criarConteudo(@Validated @RequestBody Conteudo conteudo) throws ValidationException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/conteudos").toUriString());
        return ResponseEntity.created(uri).body(conteudoServicePort.criarConteudo(conteudo));
    }

    @GetMapping
    public ResponseEntity<List<ConteudoDTO>> getConteudos() {
        return ResponseEntity.ok().body(conteudoServicePort.buscarConteudos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConteudoDTO> buscarId(@PathVariable Long id) throws ValidationException {
        return ResponseEntity.ok().body(conteudoServicePort.buscarId(id));
    }

    @GetMapping("/range")
    public ResponseEntity<List<ConteudoDTO>> obterConteudoPorRangeDeReleaseYear(@RequestParam("de") Integer de, @RequestParam("ate") Integer ate) throws ValidationException {
        return ResponseEntity.ok().body(this.conteudoServicePort.obterConteudoPorRangeDeReleaseYear(de,ate));
    }

    @GetMapping("/tipo")
    public ResponseEntity<List<ConteudoDTO>> buscarPorTipo(@RequestParam("tipo") String tipo) throws ValidationException {
        return ResponseEntity.ok().body(this.conteudoServicePort.buscarPorTipo(tipo));
    }

}
