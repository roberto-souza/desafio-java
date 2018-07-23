package br.com.robertosouza.desafio.model;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Projeto.class)
public abstract class Projeto_ {

	public static volatile SingularAttribute<Projeto, Float> orcamento;
	public static volatile SetAttribute<Projeto, Pessoa> pessoas;
	public static volatile SingularAttribute<Projeto, Pessoa> idGerente;
	public static volatile SingularAttribute<Projeto, LocalDate> dataFim;
	public static volatile SingularAttribute<Projeto, String> nome;
	public static volatile SingularAttribute<Projeto, Long> id;
	public static volatile SingularAttribute<Projeto, LocalDate> dataInicio;
	public static volatile SingularAttribute<Projeto, LocalDate> dataPrevisaoFim;
	public static volatile SingularAttribute<Projeto, String> descricao;
	public static volatile SingularAttribute<Projeto, Status> status;
	public static volatile SingularAttribute<Projeto, Risco> risco;

}

