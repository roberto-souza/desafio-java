package br.com.robertosouza.desafio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import br.com.robertosouza.desafio.dto.ChangeStatusDTO;
import br.com.robertosouza.desafio.model.Projeto;
import br.com.robertosouza.desafio.repositories.filter.ProjetoFilter;
import br.com.robertosouza.desafio.services.ProjetoService;

@RestController
@RequestMapping("/projeto")
public class ProjetoController {

	@Autowired
	private ProjetoService projetoService;
	
	@GetMapping("/{sort}/{sortAsc}")
	public Page<Projeto> findProjects(ProjetoFilter filter, Pageable pageable, @PathVariable String sort, @PathVariable Boolean sortAsc) {
		return this.projetoService.findProjects(filter, pageable, sort, sortAsc);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Projeto> get(@PathVariable Long id) {
		Projeto projeto = this.projetoService.get(id);
		return projeto != null ? ResponseEntity.ok(projeto) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Projeto> insert(@RequestBody Projeto pProjeto) {
		Projeto projeto = this.projetoService.insert(pProjeto);
		return ResponseEntity.status(HttpStatus.CREATED).body(projeto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Projeto> update(@PathVariable Long id, @RequestBody Projeto pProjeto) {
		Projeto projeto = this.projetoService.update(id, pProjeto);
		return ResponseEntity.ok(projeto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Projeto> delete(@PathVariable Long id) {
		this.projetoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/changestep")
	public ResponseEntity<Projeto> nextStepProject(@RequestBody ChangeStatusDTO changeStatusDTO) {
		Projeto projeto = this.projetoService.changeStepProject(changeStatusDTO);
		return ResponseEntity.ok(projeto);
	}
	
}
