package com.netflix.hexagonal.adapters.controllers;

import com.netflix.hexagonal.domain.exception.business.ValidationException;
import com.netflix.hexagonal.domain.dtos.ConteudoDTO;
import com.netflix.hexagonal.domain.models.Conteudo;
import com.netflix.hexagonal.domain.ports.intefaces.ConteudoServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
    @GetMapping("/titulo")
    public ResponseEntity<Page<ConteudoDTO>> buscarPorTituloPaginado(@RequestParam(value="titulo", defaultValue = "", required = false) String titulo, @RequestParam(value = "page", defaultValue = "0", required = false) int page, @RequestParam(value = "size", defaultValue = "1000", required = false) int size){
        return ResponseEntity.ok().body(this.conteudoServicePort.buscarPorTituloPaginado(titulo, page,size));
    }
    @DeleteMapping("/remover/{id}")
    public ResponseEntity removerConteudoPorId(@PathVariable Long id) throws ValidationException {
        conteudoServicePort.removerConteudoPorId(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/image/atualizar/{id}")
    public ResponseEntity<ConteudoDTO> atualizarImagem(@PathVariable Long id, @RequestParam("image") MultipartFile file) throws ValidationException {
        return ResponseEntity.ok().body(conteudoServicePort.atualizarImagem(id,file));
    }
    @DeleteMapping("/image/remover/{id}")
    public ResponseEntity removerImagem(@PathVariable Long id) throws ValidationException {
        conteudoServicePort.removerImagem(id);
        return ResponseEntity.ok().build();
    }
}
