package com.algaworks.brewer.repository.helper.estilo;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.filter.EstiloFilter;
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

public class EstiloRepositoryImpl implements EstiloRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PaginacaoUtil paginacaoUtil;
    
    @Override
    @Transactional(readOnly = true)
    public Page<Estilo> filtrar(EstiloFilter filtro, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Estilo> criteria = builder.createQuery(Estilo.class);
        Root<Estilo> root = criteria.from(Estilo.class);
  
     
        List<Predicate> predicates = adicionarFiltros(filtro, builder, root);
        criteria.where(predicates.toArray(new Predicate[0]));
            
     // ✅ AQUI ENTRA A ORDENAÇÃO
        paginacaoUtil.prepararOrdem(builder, criteria, root,  pageable);
        
        // Cria query já configurada
        TypedQuery<Estilo> query = manager.createQuery(criteria);   

        paginacaoUtil.prepararPaginacao(query, pageable);
        
        List<Estilo> estilos = query.getResultList();
        Long total = total(filtro);

        return new PageImpl<>(estilos, pageable, total);
    }
    
    private Long total(EstiloFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Estilo> root = query.from(Estilo.class);

        List<Predicate> predicates = adicionarFiltros(filtro, builder, root);
        query.where(predicates.toArray(new Predicate[0]));
        query.select(builder.count(root));

        return manager.createQuery(query).getSingleResult();
    }

    private List<Predicate> adicionarFiltros(EstiloFilter filtro, CriteriaBuilder builder, Root<Estilo> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (filtro != null) {
            if (StringUtils.hasText(filtro.getDescricao())) {
                predicates.add(builder.like(builder.lower(root.get("descricao")),"%" + filtro.getDescricao().toLowerCase() + "%"));
            }
        }

        return predicates;
    }
}
