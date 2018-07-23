package br.com.robertosouza.desafio.repositories.filter;

import java.time.LocalDate;

import br.com.robertosouza.desafio.model.Risco;
import br.com.robertosouza.desafio.model.Status;

public class ProjetoFilter {
	
	private String name;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	private Status status;
	private Risco risco;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}
	public LocalDate getDataFim() {
		return dataFim;
	}
	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Risco getRisco() {
		return risco;
	}
	public void setRisco(Risco risco) {
		this.risco = risco;
	}

}
