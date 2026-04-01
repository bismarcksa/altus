package com.algaworks.brewer.repository.paginacao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

@Component
public class PaginacaoUtil {

	public void prepararOrdem(CriteriaBuilder builder, CriteriaQuery<?> criteria, Root<?> root, Pageable pageable) {
		Sort sort = pageable.getSort();

	    if (sort == null || !sort.isSorted()) {
	        return;
	    }

	    List<jakarta.persistence.criteria.Order> orders = new ArrayList<>();

	    for (Sort.Order order : sort) {

	        Path<?> path = root;
	        String[] propriedades = order.getProperty().split("\\.");

	        for (String propriedade : propriedades) {
	            path = path.get(propriedade);
	        }

	        if (order.isAscending()) {
	            orders.add(builder.asc(path));
	        } else {
	            orders.add(builder.desc(path));
	        }
	    }

	    criteria.orderBy(orders);
	}
	
	public void prepararPaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int registrosPorPagina = pageable.getPageSize();
        int primeiroRegistro = paginaAtual * registrosPorPagina;

        query.setFirstResult(primeiroRegistro);
        query.setMaxResults(registrosPorPagina);
    }
}
