package br.com.robertosouza.desafio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.robertosouza.desafio.exception.BusinessException;
import br.com.robertosouza.desafio.model.Pessoa;
import br.com.robertosouza.desafio.repositories.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	/**
	 * Busca todas as pessoas
	 * 
	 * @return
	 */
	public List<Pessoa> findAll() {
		return this.pessoaRepository.findAll();
	}

	/**
	 * Busca uma pessoa conforme o id
	 * 
	 * @param id
	 * @return
	 */
	public Pessoa get(Long id) {
		Optional<Pessoa> pessoa = this.pessoaRepository.findById(id);
		if (pessoa.isPresent()) {
			return pessoa.get();
		} else {
			throw new BusinessException("Pessoa não encontrada!");
		}
	}

	/**
	 * Salva uma nova pessoa
	 * 
	 * @param pessoa
	 * @return
	 */
	public Pessoa insert(Pessoa pessoa) {
		return this.pessoaRepository.save(pessoa);
	}

	/**
	 * Atualiza uma pessoa conforme os dados
	 * 
	 * @param id
	 * @param pPessoa
	 * @return
	 */
	public Pessoa update(Long id, Pessoa pPessoa) {
		Pessoa pessoa = this.get(id);
		BeanUtils.copyProperties(pPessoa, pessoa, "id");
		return this.pessoaRepository.save(pessoa);
	}

	/**
	 * Exclui uma pessoa conforme o id passado
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		this.pessoaRepository.deleteById(id);
	}

}
