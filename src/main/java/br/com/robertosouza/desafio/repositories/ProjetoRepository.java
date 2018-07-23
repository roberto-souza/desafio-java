package br.com.robertosouza.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.robertosouza.desafio.model.Projeto;
import br.com.robertosouza.desafio.repositories.Projeto.ProjetoRepositoryQuery;

public interface ProjetoRepository extends JpaRepository<Projeto, Long>, ProjetoRepositoryQuery {

}
