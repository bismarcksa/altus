package com.altus.repository.helper.departamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.altus.model.Departamento;
import com.altus.repository.filter.DepartamentoFilter;
import com.altus.repository.paginacao.PaginacaoUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class DepartamentosImpl implements DepartamentosQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PaginacaoUtil paginacaoUtil;
    
    @Override
    @Transactional(readOnly = true)
    public Page<Departamento> filtrar(DepartamentoFilter filtro, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Departamento> criteria = builder.createQuery(Departamento.class);
        Root<Departamento> root = criteria.from(Departamento.class);
  
     
        List<Predicate> predicates = adicionarFiltros(filtro, builder, root);
        criteria.where(predicates.toArray(new Predicate[0]));
            
        // ✅ AQUI ENTRA A ORDENAÇÃO
        paginacaoUtil.prepararOrdem(builder, criteria, root,  pageable);
        
        // Cria query já configurada
        TypedQuery<Departamento> query = manager.createQuery(criteria);   

        paginacaoUtil.prepararPaginacao(query, pageable);
        
        List<Departamento> estilos = query.getResultList();
        Long total = total(filtro);

        return new PageImpl<>(estilos, pageable, total);
    }
    
    private Long total(DepartamentoFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Departamento> root = query.from(Departamento.class);

        List<Predicate> predicates = adicionarFiltros(filtro, builder, root);
        query.where(predicates.toArray(new Predicate[0]));
        query.select(builder.count(root));

        return manager.createQuery(query).getSingleResult();
    }

    private List<Predicate> adicionarFiltros(DepartamentoFilter filtro, CriteriaBuilder builder, Root<Departamento> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (filtro != null) {
            if (StringUtils.hasText(filtro.getNome())) {
                predicates.add(builder.like(builder.lower(root.get("nome")),"%" + filtro.getNome().toLowerCase() + "%"));
            }
        }

        return predicates;
    }
}
