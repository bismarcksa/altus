package com.algaworks.brewer.repository.helper.cidade;

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.filter.CidadeFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

public class CidadeRepositoryImpl implements CidadeRepositoryQueries {
   
	@PersistenceContext
    private EntityManager manager;

    @Autowired
    private PaginacaoUtil paginacaoUtil;

    @Override
    @Transactional(readOnly = true)
    public Page<Cidade> filtrar(CidadeFilter filtro, Pageable pageable) {
    	CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Cidade> criteria = builder.createQuery(Cidade.class);
        Root<Cidade> root = criteria.from(Cidade.class);
  
        // FETCH DO ESTADO
        root.fetch("estado", JoinType.INNER);
        criteria.distinct(true);
               
        List<Predicate> predicates = adicionarFiltros(filtro, builder, root);
        criteria.where(predicates.toArray(new Predicate[0]));       
         
        //  AQUI ENTRA A ORDENAÇÃO
        paginacaoUtil.prepararOrdem(builder, criteria, root,  pageable);
        
        // Cria query já configurada
        TypedQuery<Cidade> query = manager.createQuery(criteria);     

        paginacaoUtil.prepararPaginacao(query, pageable);
        
        List<Cidade> cidades = query.getResultList();
        Long total = total(filtro);

        return new PageImpl<>(cidades, pageable, total);
    }
    
    private Long total(CidadeFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Cidade> root = query.from(Cidade.class);

        List<Predicate> predicates = adicionarFiltros(filtro, builder, root);
        query.where(predicates.toArray(new Predicate[0]));
        query.select(builder.count(root));

        return manager.createQuery(query).getSingleResult();
    }

    private List<Predicate> adicionarFiltros(CidadeFilter filtro, CriteriaBuilder builder, Root<Cidade> root) {
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
