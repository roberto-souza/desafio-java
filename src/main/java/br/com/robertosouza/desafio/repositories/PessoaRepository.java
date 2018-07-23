package br.com.robertosouza.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.robertosouza.desafio.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
