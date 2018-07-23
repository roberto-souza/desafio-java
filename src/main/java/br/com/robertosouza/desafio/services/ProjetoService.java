package br.com.robertosouza.desafio.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.robertosouza.desafio.dto.ChangeStatusDTO;
import br.com.robertosouza.desafio.exception.BusinessException;
import br.com.robertosouza.desafio.model.Pessoa;
import br.com.robertosouza.desafio.model.Projeto;
import br.com.robertosouza.desafio.model.Status;
import br.com.robertosouza.desafio.repositories.ProjetoRepository;
import br.com.robertosouza.desafio.repositories.filter.ProjetoFilter;

@Service
public class ProjetoService {

	@Autowired
	private ProjetoRepository projetoRepository;
	
	@Autowired
	private PessoaService pessoaService;

	/**
	 * Busca os projetos conforme os parametros e realiza uma paginacao dos dados
	 * 
	 * @param filter
	 * @param pageable
	 * @param sort
	 * @param sortDirectionAsc
	 * @return Page de Projetos
	 */
	public Page<Projeto> findProjects(ProjetoFilter filter, Pageable pageable, String sort, Boolean sortDirectionAsc) {
		return this.projetoRepository.filter(filter, pageable, sort, sortDirectionAsc);
	}

	/**
	 * Busca um projeto conforme o id
	 * 
	 * @param id
	 * @return Projeto
	 */
	public Projeto get(Long id) {
		Optional<Projeto> pessoa = this.projetoRepository.findById(id);
		if (pessoa.isPresent()) {
			return pessoa.get();
		} else {
			throw new BusinessException("Projeto não encontrado!");
		}
	}

	/**
	 * Salva um novo projeto
	 * 
	 * @param projeto
	 * @return Novo Projeto
	 */
	public Projeto insert(Projeto projeto) {
		projeto.setStatus(Status.EM_ANALISE);
		
		Set<Pessoa> pessoas = new HashSet<>();
		if(!projeto.getPessoas().isEmpty()) {
			pessoas.addAll(projeto.getPessoas());
		}

		Pessoa gerente = this.pessoaService.get(projeto.getIdGerente().getId());
		pessoas.add(gerente);

		if(!pessoas.isEmpty()) {
			pessoas.forEach(pPessoa -> {
				Pessoa pessoa = this.pessoaService.get(pPessoa.getId());
				if(!pessoa.getFuncionario()) { 
					throw new BusinessException(pessoa.getNome() + " não é funcionário e não pode participar deste projeto!");
				}
			});
			
			projeto.setPessoas(pessoas);
		}
		
		return this.projetoRepository.save(projeto);
	}

	/**
	 * Atualiza um projeto conforme os dados passados
	 * 
	 * @param id
	 * @param pProjeto
	 * @return Projeto atualizado
	 */
	public Projeto update(Long id, Projeto pProjeto) {
		Projeto projeto = this.get(id);
		BeanUtils.copyProperties(pProjeto, projeto, "id");
		return this.projetoRepository.save(projeto);
	}

	/**
	 * Verifica o status do projeto e exclui se o status estiver de acordo
	 * 
	 * @param id
	 */
	public void delete(Long id) {
		Projeto projeto = this.get(id);
		Status status = projeto.getStatus();

		if (!status.equals(Status.INICIADO) && !status.equals(Status.EM_ANDAMENTO)
				&& !status.equals(Status.ENCERRADO)) {
			this.projetoRepository.deleteById(id);
		} else {
			throw new BusinessException("Este projeto não pode ser excluído com este status");
		}
	}

	/**
	 * Altera o status de um projeto
	 * 
	 * @param changeStatusDTO
	 */
	public Projeto changeStepProject(ChangeStatusDTO changeStatusDTO) {
		Projeto projeto = this.get(changeStatusDTO.getId());
		Status status = this.validateStatus(changeStatusDTO.getStatus().toString());

		if (status != null) {
			projeto.setStatus(status);
			return this.projetoRepository.save(projeto);
		} else {
			throw new BusinessException("Status inválido");
		}
	}

	/**
	 * Verifica se o status passado por parametro e valido
	 * 
	 * @param pStatus
	 * @return Status
	 */
	private Status validateStatus(String pStatus) {
		for (Status status : Status.values()) {
			if (status.toString().equalsIgnoreCase(pStatus)) {
				return status;
			}
		}

		return null;
	}

}
