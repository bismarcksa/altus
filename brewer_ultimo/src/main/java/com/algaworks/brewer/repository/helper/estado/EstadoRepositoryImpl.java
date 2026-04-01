package com.algaworks.brewer.repository.helper.estado;

import com.algaworks.brewer.model.Estado;
import com.algaworks.brewer.repository.filter.EstadoFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class EstadoRepositoryImpl implements EstadoRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PaginacaoUtil paginacaoUtil;
    
    @Override
    @Transactional(readOnly = true)
    public Page<Estado> filtrar(EstadoFilter filtro, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Estado> criteria = builder.createQuery(Estado.class);
        Root<Estado> root = criteria.from(Estado.class);
  
     
        List<Predicate> predicates = adicionarFiltros(filtro, builder, root);
        criteria.where(predicates.toArray(new Predicate[0]));
        
        // Ordenação dinâmica
//        aplicarOrdenacao(pageable, builder, criteria, root);
            
        // Cria query já configurada
        TypedQuery<Estado> query = manager.createQuery(criteria);
//        aplicarPaginacao(query, pageable);     

        paginacaoUtil.prepararPaginacao(query, pageable);
        
        List<Estado> estados = query.getResultList();
        Long total = total(filtro);

        return new PageImpl<>(estados, pageable, total);
    }
    
/*    private void aplicarPaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int registrosPorPagina = pageable.getPageSize();
        int primeiroRegistro = paginaAtual * registrosPorPagina;

        query.setFirstResult(primeiroRegistro);
        query.setMaxResults(registrosPorPagina);
    }
    
    private void aplicarOrdenacao(Pageable pageable, CriteriaBuilder builder, CriteriaQuery<?> criteria, Root<Estilo> root) {
        Sort sort = pageable.getSort();
        if (sort != null && sort.isSorted()) {
            List<jakarta.persistence.criteria.Order> orders = new ArrayList<>();
            for (Sort.Order order : sort) {
                if (order.isAscending()) {
                    orders.add(builder.asc(root.get(order.getProperty())));
                } else {
                    orders.add(builder.desc(root.get(order.getProperty())));
                }
            }
            criteria.orderBy(orders);
        }
    }*/

    private Long total(EstadoFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Estado> root = query.from(Estado.class);

        List<Predicate> predicates = adicionarFiltros(filtro, builder, root);
        query.where(predicates.toArray(new Predicate[0]));
        query.select(builder.count(root));

        return manager.createQuery(query).getSingleResult();
    }

    private List<Predicate> adicionarFiltros(EstadoFilter filtro, CriteriaBuilder builder, Root<Estado> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (filtro != null) {
            if (StringUtils.hasText(filtro.getNome())) {
                predicates.add(builder.like(
                        builder.lower(root.get("nome")),
                        "%" + filtro.getNome().toLowerCase() + "%"
                ));
            }
        }

        return predicates;
    }
}
