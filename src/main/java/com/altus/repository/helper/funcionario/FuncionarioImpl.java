package com.altus.repository.helper.funcionario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.altus.model.Funcionario;
import com.altus.repository.filter.FuncionarioFilter;
import com.altus.repository.paginacao.PaginacaoUtil;

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

public class FuncionarioImpl implements FuncionarioQueries {

    @PersistenceContext
    private EntityManager manager;
    
    @Autowired
    private PaginacaoUtil paginacaoUtil;
    
//    @Autowired
//    private FotoStorage fotoStorage;

    @Override
    @Transactional(readOnly = true)
    public Page<Funcionario> filtrar(FuncionarioFilter filtro, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Funcionario> criteria = builder.createQuery(Funcionario.class);
        Root<Funcionario> root = criteria.from(Funcionario.class);
  
        // FETCH JOIN
//        root.fetch("estilo", JoinType.LEFT);   // Força o fetch do estilo para evitar N+1, Fetch join apenas para a listagem (não afeta a contagem)
        
        List<Predicate> predicates = adicionarFiltros(filtro, builder, root);
        criteria.where(predicates.toArray(new Predicate[0]));    
         
        //  AQUI ENTRA A ORDENAÇÃO
//        paginacaoUtil.prepararOrdem(builder, criteria, root,  pageable);
        
        // Cria query já configurada
        TypedQuery<Funcionario> query = manager.createQuery(criteria); 
              
        paginacaoUtil.prepararPaginacao(query, pageable);

        List<Funcionario> funcionarios = query.getResultList();
        Long total = total(filtro);

        return new PageImpl<>(funcionarios, pageable, total);
    }

    private Long total(FuncionarioFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Funcionario> root = query.from(Funcionario.class);

        List<Predicate> predicates = adicionarFiltros(filtro, builder, root);
        query.where(predicates.toArray(new Predicate[0]));
        query.select(builder.count(root));

        return manager.createQuery(query).getSingleResult();
    }

    private List<Predicate> adicionarFiltros(FuncionarioFilter filtro, CriteriaBuilder builder, Root<Funcionario> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (filtro != null) {
            if (StringUtils.hasText(filtro.getNome())) {
                predicates.add(builder.like(builder.lower(root.get("nome")),"%" + filtro.getNome().toLowerCase() + "%"));
            }
 
        }

        return predicates;
    }
    
/*    @SuppressWarnings("unused")
	private boolean isEstiloPresent(CervejaFilter filter) {
        return filter.getEstilo() != null && filter.getEstilo().getCodigo() != null;
    }

    @Override
    public List<CervejaDTO> porSkuOuNome(String skuOuNome) {
        String jpql = "Select new com.algaworks.brewer.dto.CervejaDTO(codigo, sku, nome, origem, valor, foto) " +
                " from Cerveja where lower(sku) like lower(:skuOuNome) or lower(nome) like lower(:skuOuNome)";

        List<CervejaDTO> cervejasFiltradas = manager.createQuery(jpql,CervejaDTO.class).setParameter("skuOuNome",skuOuNome + "%").getResultList();
        cervejasFiltradas.forEach(c-> c.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + c.getFoto())));

        return cervejasFiltradas;
    }*/
}
