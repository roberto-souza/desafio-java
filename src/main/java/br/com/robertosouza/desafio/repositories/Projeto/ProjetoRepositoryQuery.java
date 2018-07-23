package br.com.robertosouza.desafio.repositories.Projeto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.robertosouza.desafio.model.Projeto;
import br.com.robertosouza.desafio.repositories.filter.ProjetoFilter;

public interface ProjetoRepositoryQuery {

	public Page<Projeto> filter(ProjetoFilter filter, Pageable pageable, String sort, Boolean sortDirectionAsc);
	
}
