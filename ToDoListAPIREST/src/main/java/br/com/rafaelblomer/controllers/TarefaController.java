package br.com.rafaelblomer.controllers;

import br.com.rafaelblomer.business.dtos.in.TarefaDtoRequest;
import br.com.rafaelblomer.business.dtos.out.TarefaDtoResponse;
import br.com.rafaelblomer.business.services.ITarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private ITarefaService service;

    @PostMapping
    public ResponseEntity<TarefaDtoResponse> criarTarefa(@RequestBody TarefaDtoRequest dtoRequest) {
        TarefaDtoResponse tarefa = service.criarTarefa(dtoRequest);
        return ResponseEntity.ok().body(tarefa);
    }

    @GetMapping
    public ResponseEntity<List<TarefaDtoResponse>> retornarTodasTarefas() {
    	List<TarefaDtoResponse> list = service.buscarTodasTarefas();
        return ResponseEntity.ok().body(list);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TarefaDtoResponse> retornarUmaTarefa(@PathVariable("id") Long id) {
    	return ResponseEntity.ok().body(service.buscarUmaTarefa(id));
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<TarefaDtoResponse> alterarStatusTarefa(@PathVariable("id") Long id) {
    	return ResponseEntity.ok().body(service.alterarStatusTarefa(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TarefaDtoResponse> alterarDadosTarefa(@PathVariable("id") Long id, @RequestBody TarefaDtoRequest dtoRequest) {
    	return ResponseEntity.ok().body(service.alterarDadosTarefa(id, dtoRequest));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTarefa(@PathVariable("id") Long id) {
    	service.excluirTarefa(id);
    	return ResponseEntity.noContent().build();
    }
}
