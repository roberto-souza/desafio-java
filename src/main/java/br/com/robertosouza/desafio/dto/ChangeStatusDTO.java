package br.com.robertosouza.desafio.dto;

import br.com.robertosouza.desafio.model.Status;

public class ChangeStatusDTO {
	
	private Long id;
	private Status status;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
}
