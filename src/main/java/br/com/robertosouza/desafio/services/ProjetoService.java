package br.com.robertosouza.desafio.services;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.robertosouza.desafio.exception.BusinessException;
import br.com.robertosouza.desafio.model.Projeto;
import br.com.robertosouza.desafio.model.Status;
import br.com.robertosouza.desafio.repositories.ProjetoRepository;
import br.com.robertosouza.desafio.repositories.filter.ProjetoFilter;

@Service
public class ProjetoService {

	@Autowired
	private ProjetoRepository projetoRepository;
	
	public Page<Projeto> findProjects(ProjetoFilter filter, Pageable pageable, String sort, Boolean sortDirectionAsc) {
		return this.projetoRepository.filter(filter, pageable, sort, sortDirectionAsc);
	}

	public Projeto get(Long id) {
		Optional<Projeto> pessoa = this.projetoRepository.findById(id);
		if (pessoa.isPresent()) {
			return pessoa.get();
		} else {
			throw new BusinessException("Projeto não encontrado!");
		}
	}

	public Projeto insert(Projeto projeto) {
		projeto.setStatus(Status.EM_DESENVOLVIMENTO);
		return this.projetoRepository.save(projeto);
	}

	public Projeto update(Long id, Projeto pProjeto) {
		Projeto projeto = this.get(id);
		BeanUtils.copyProperties(pProjeto, projeto, "id");
		return this.projetoRepository.save(projeto);
	}

	public void delete(Long id) {
		this.projetoRepository.deleteById(id);
	}

	public void nextStepProject(Long id) {
		Projeto projeto = this.get(id);
		if (projeto.getStatus().equals(Status.EM_DESENVOLVIMENTO)) {
			projeto.setStatus(Status.TESTES_INTERNOS);
		} else if (projeto.getStatus().equals(Status.TESTES_INTERNOS)) {
			projeto.setStatus(Status.ENVIADO_DEPLOY);
		} else if (projeto.getStatus().equals(Status.ENVIADO_DEPLOY)) {
			projeto.setStatus(Status.TESTES_CLIENTE);
		} else if (projeto.getStatus().equals(Status.TESTES_CLIENTE)) {
			projeto.setStatus(Status.PRODUCAO);
		}
		this.projetoRepository.save(projeto);
	}

	public void previousStepProject(Long id) {
		Projeto projeto = this.get(id);
		if (projeto.getStatus().equals(Status.PRODUCAO)) {
			projeto.setStatus(Status.TESTES_CLIENTE);
		} else if (projeto.getStatus().equals(Status.TESTES_CLIENTE)) {
			projeto.setStatus(Status.ENVIADO_DEPLOY);
		} else if (projeto.getStatus().equals(Status.ENVIADO_DEPLOY)) {
			projeto.setStatus(Status.TESTES_INTERNOS);
		} else if (projeto.getStatus().equals(Status.TESTES_INTERNOS)) {
			projeto.setStatus(Status.EM_DESENVOLVIMENTO);
		}
		this.projetoRepository.save(projeto);
	}

}
