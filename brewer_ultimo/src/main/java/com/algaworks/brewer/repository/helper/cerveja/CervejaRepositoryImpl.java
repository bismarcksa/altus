package com.algaworks.brewer.repository.helper.cerveja;

import com.algaworks.brewer.dto.CervejaDTO;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.filter.CervejaFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;
import com.algaworks.brewer.storage.FotoStorage;

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

public class CervejaRepositoryImpl implements CervejaRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;
    
    @Autowired
    private PaginacaoUtil paginacaoUtil;
    
    @Autowired
    private FotoStorage fotoStorage;

    @Override
    @Transactional(readOnly = true)
    public Page<Cerveja> filtrar(CervejaFilter filtro, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Cerveja> criteria = builder.createQuery(Cerveja.class);
        Root<Cerveja> root = criteria.from(Cerveja.class);
  
        // FETCH JOIN
        root.fetch("estilo", JoinType.LEFT);   // Força o fetch do estilo para evitar N+1, Fetch join apenas para a listagem (não afeta a contagem)
        
        List<Predicate> predicates = adicionarFiltros(filtro, builder, root);
        criteria.where(predicates.toArray(new Predicate[0]));    
         
        //  AQUI ENTRA A ORDENAÇÃO
        paginacaoUtil.prepararOrdem(builder, criteria, root,  pageable);
        
        // Cria query já configurada
        TypedQuery<Cerveja> query = manager.createQuery(criteria); 
              
        paginacaoUtil.prepararPaginacao(query, pageable);

        List<Cerveja> cervejas = query.getResultList();
        Long total = total(filtro);

        return new PageImpl<>(cervejas, pageable, total);
    }

    private Long total(CervejaFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Cerveja> root = query.from(Cerveja.class);

        List<Predicate> predicates = adicionarFiltros(filtro, builder, root);
        query.where(predicates.toArray(new Predicate[0]));
        query.select(builder.count(root));

        return manager.createQuery(query).getSingleResult();
    }

    private List<Predicate> adicionarFiltros(CervejaFilter filtro, CriteriaBuilder builder, Root<Cerveja> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (filtro != null) {
            if (StringUtils.hasText(filtro.getSku())) {
                predicates.add(builder.equal(root.get("sku"), filtro.getSku()));
            }

            if (StringUtils.hasText(filtro.getNome())) {
                predicates.add(builder.like(builder.lower(root.get("nome")),"%" + filtro.getNome().toLowerCase() + "%"));
            }

            if (filtro.getEstilo() != null && filtro.getEstilo().getCodigo() != null) {
                predicates.add(builder.equal(root.get("estilo").get("codigo"), filtro.getEstilo().getCodigo()));
            }

            if (filtro.getSabor() != null) {
                predicates.add(builder.equal(root.get("sabor"), filtro.getSabor()));
            }

            if (filtro.getOrigem() != null) {
                predicates.add(builder.equal(root.get("origem"), filtro.getOrigem()));
            }

            if (filtro.getValorDe() != null) {
                predicates.add(builder.ge(root.get("valor"), filtro.getValorDe()));
            }

            if (filtro.getValorAte() != null) {
                predicates.add(builder.le(root.get("valor"), filtro.getValorAte()));
            }
        }

        return predicates;
    }
    
    @SuppressWarnings("unused")
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
    }
}
