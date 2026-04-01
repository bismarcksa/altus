package com.algaworks.brewer.repository.helper.cliente;

import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.repository.filter.ClienteFilter;
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
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepositoryImpl implements ClienteRepositoryQueries {

	@PersistenceContext
    private EntityManager manager;
    
    @Autowired
    private PaginacaoUtil paginacaoUtil;

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> filtrar(ClienteFilter filtro, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Cliente> criteria = builder.createQuery(Cliente.class);
        Root<Cliente> root = criteria.from(Cliente.class);

        // ✅ Faz fetch corretamente em entidades aninhadas
        var endereco = root.fetch("endereco", JoinType.LEFT);
        endereco.fetch("cidade", JoinType.LEFT)
                .fetch("estado", JoinType.LEFT);

        List<Predicate> predicates = adicionarFiltros(filtro, builder, root);
        criteria.where(predicates.toArray(new Predicate[0]));

        //  AQUI ENTRA A ORDENAÇÃO
        paginacaoUtil.prepararOrdem(builder, criteria, root,  pageable);
        
        TypedQuery<Cliente> query = manager.createQuery(criteria);

        paginacaoUtil.prepararPaginacao(query, pageable);

        List<Cliente> clientes = query.getResultList();
        Long total = total(filtro);

        return new PageImpl<>(clientes, pageable, total);
    }
    

    private Long total(ClienteFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Cliente> root = query.from(Cliente.class);

        List<Predicate> predicates = adicionarFiltros(filtro, builder, root);
        query.where(predicates.toArray(new Predicate[0]));
        query.select(builder.count(root));

        return manager.createQuery(query).getSingleResult();
    }

    private List<Predicate> adicionarFiltros(ClienteFilter filtro, CriteriaBuilder builder, Root<Cliente> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (filtro != null) {          
            if (StringUtils.hasText(filtro.getNome())) {
                predicates.add(builder.like(
                        builder.lower(root.get("nome")),
                        "%" + filtro.getNome().toLowerCase() + "%"
                ));
            }
            if (filtro.getCpfOuCnpj() != null) {       
                predicates.add(builder.equal(root.get("cpfOuCnpj"), filtro.getCpfOuCnpj()));
            }
            
        }

        return predicates;
    }
}
