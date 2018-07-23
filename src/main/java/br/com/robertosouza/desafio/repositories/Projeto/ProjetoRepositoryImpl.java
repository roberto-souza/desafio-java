package br.com.robertosouza.desafio.repositories.Projeto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.robertosouza.desafio.model.Projeto;
import br.com.robertosouza.desafio.model.Projeto_;
import br.com.robertosouza.desafio.repositories.filter.ProjetoFilter;

public class ProjetoRepositoryImpl implements ProjetoRepositoryQuery {

	private static final String DATA_INICIO = "dataInicio";
	private static final String NOME = "nome";
	private static final String RISCO = "risco";
	private static final String STATUS = "status";
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Projeto> filter(ProjetoFilter filter, Pageable pageable, String sort, Boolean sortDirectionAsc) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Projeto> criteria = builder.createQuery(Projeto.class);
		Root<Projeto> root = criteria.from(Projeto.class);

		Predicate[] predicates = this.createRestrictions(filter, builder, root);
		criteria.where(predicates);
		if (sort.equals(NOME)) {
			if (sortDirectionAsc) {
				criteria.orderBy(builder.asc(root.get(Projeto_.nome)));
			} else {
				criteria.orderBy(builder.desc(root.get(Projeto_.nome)));
			}
		} else if (sort.equals(DATA_INICIO)) {
			if (sortDirectionAsc) {
				criteria.orderBy(builder.asc(root.get(Projeto_.dataInicio)));
			} else {
				criteria.orderBy(builder.desc(root.get(Projeto_.dataInicio)));
			}
		} else if (sort.equals(STATUS)) {
			if (sortDirectionAsc) {
				criteria.orderBy(builder.asc(root.get(Projeto_.status.toString())));
			} else {
				criteria.orderBy(builder.desc(root.get(Projeto_.status.toString())));
			}
		} else if (sort.equals(RISCO)) {
			if (sortDirectionAsc) {
				criteria.orderBy(builder.asc(root.get(Projeto_.risco.toString())));
			} else {
				criteria.orderBy(builder.desc(root.get(Projeto_.risco.toString())));
			}
		}
		
		TypedQuery<Projeto> query = manager.createQuery(criteria);
		this.addRestrictionsOfPagination(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, this.total(filter));
	}

	private Predicate[] createRestrictions(ProjetoFilter filter, CriteriaBuilder builder, Root<Projeto> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (filter.getName() != null) {
			predicates.add(
					builder.like(builder.lower(root.get(Projeto_.nome)), "%" + filter.getName().toLowerCase() + "%"));
		}

		if (filter.getDataInicio() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(Projeto_.dataInicio), filter.getDataInicio()));
		}

		if (filter.getDataFim() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Projeto_.dataFim), filter.getDataFim()));
		}

		if (filter.getStatus() != null) {
			predicates.add(builder.like(builder.upper(root.get(Projeto_.status.toString())),
					filter.getStatus().toString().toUpperCase()));
		}

		if (filter.getRisco() != null) {
			predicates.add(builder.like(builder.upper(root.get(Projeto_.risco.toString())),
					filter.getRisco().toString().toUpperCase()));
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void addRestrictionsOfPagination(TypedQuery<Projeto> query, Pageable pageable) {
		int currentPage = pageable.getPageNumber();
		int totalRegistrationPerPage = pageable.getPageSize();
		int firstRecordOfPage = currentPage * totalRegistrationPerPage;

		query.setFirstResult(firstRecordOfPage);
		query.setMaxResults(totalRegistrationPerPage);
	}

	private Long total(ProjetoFilter filter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Projeto> root = criteria.from(Projeto.class);

		Predicate[] predicates = this.createRestrictions(filter, builder, root);
		criteria.where(predicates);

		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}

}
