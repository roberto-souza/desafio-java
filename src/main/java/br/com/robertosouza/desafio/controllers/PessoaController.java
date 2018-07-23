package br.com.robertosouza.desafio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.robertosouza.desafio.model.Pessoa;
import br.com.robertosouza.desafio.services.PessoaService;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping
	public List<Pessoa> findAll() {
		return this.pessoaService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> get(@PathVariable Long id) {
		Pessoa pessoa = this.pessoaService.get(id);
		return pessoa != null ? ResponseEntity.ok(pessoa) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> insert(@RequestBody Pessoa pPessoa) {
		Pessoa pessoa = this.pessoaService.insert(pPessoa);
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> update(@PathVariable Long id, @RequestBody Pessoa pPessoa) {
		Pessoa pessoa = this.pessoaService.update(id, pPessoa);
		return ResponseEntity.ok(pessoa);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Pessoa> delete(@PathVariable Long id) {
		this.pessoaService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
