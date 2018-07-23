package br.com.robertosouza.desafio.model;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "projeto")
public class Projeto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "nome", length = 200)
	private String nome;
	
	@Column(name = "data_inicio")
	private LocalDate dataInicio;
	
	@Column(name = "data_previsao_fim")
	private LocalDate dataPrevisaoFim;
	
	@Column(name = "data_fim")
	private LocalDate dataFim;
	
	@Column(name = "descricao", length = 5000)
	private String descricao;
	
	@Column(name = "status", length = 45)
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(name = "orcamento")
	private Float orcamento;
	
	@Column(name = "risco", length = 45)
	@Enumerated(EnumType.STRING)
	private Risco risco;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "idgerente")
	private Pessoa idGerente;
	
	@ManyToMany(mappedBy = "projetos")
	private Set<Pessoa> pessoas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataPrevisaoFim() {
		return dataPrevisaoFim;
	}

	public void setDataPrevisaoFim(LocalDate dataPrevisaoFim) {
		this.dataPrevisaoFim = dataPrevisaoFim;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Float getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Float orcamento) {
		this.orcamento = orcamento;
	}

	public Risco getRisco() {
		return risco;
	}

	public void setRisco(Risco risco) {
		this.risco = risco;
	}

	public Pessoa getIdGerente() {
		return idGerente;
	}

	public void setIdGerente(Pessoa idGerente) {
		this.idGerente = idGerente;
	}

	public Set<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(Set<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Projeto other = (Projeto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Projeto [id=" + id + ", nome=" + nome + ", dataInicio=" + dataInicio + ", dataPrevisaoFim="
				+ dataPrevisaoFim + ", dataFim=" + dataFim + ", descricao=" + descricao + ", status=" + status
				+ ", orcamento=" + orcamento + ", risco=" + risco + ", idGerente=" + idGerente + ", pessoas=" + pessoas
				+ "]";
	}
	
}
