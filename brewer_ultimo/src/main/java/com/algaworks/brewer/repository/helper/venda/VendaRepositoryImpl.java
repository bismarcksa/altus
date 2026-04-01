package com.algaworks.brewer.repository.helper.venda;

import com.algaworks.brewer.dto.VendaMes;
import com.algaworks.brewer.dto.VendaOrigem;
import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.model.StatusVenda;
import com.algaworks.brewer.model.TipoPessoa;
import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.filter.VendaFilter;
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
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class VendaRepositoryImpl implements VendaRepositoryQueries {
	

	@PersistenceContext
    private EntityManager manager;

    @Autowired
    private PaginacaoUtil paginacaoUtil;

    @SuppressWarnings("unused")
	@Override
    @Transactional(readOnly = true)
    public Page<Venda> filtrar(VendaFilter vendaFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Venda> criteria = builder.createQuery(Venda.class);
        Root<Venda> root = criteria.from(Venda.class);
  
        // FETCH JOIN
        Join<Venda, Cliente> cliente = root.join("cliente", JoinType.LEFT);
        
        List<Predicate> predicates = adicionarFiltros(vendaFilter, builder, root);
        criteria.where(predicates.toArray(new Predicate[0]));    
        
        //  AQUI ENTRA A ORDENAÇÃO
        paginacaoUtil.prepararOrdem(builder, criteria, root,  pageable);
        
        // Cria query já configurada
        TypedQuery<Venda> query = manager.createQuery(criteria); 
              
        paginacaoUtil.prepararPaginacao(query, pageable);

        List<Venda> vendas = query.getResultList();
        Long total = total(vendaFilter);

        return new PageImpl<>(vendas, pageable, total);
    }
    

    @Override
    @Transactional(readOnly = true)
    public Venda buscarVendaComItens(Long codigo) {
        return manager.createQuery(
            "select distinct v from Venda v " +
            "left join fetch v.itens " +
            "where v.codigo = :codigo", Venda.class)
            .setParameter("codigo", codigo)
            .getSingleResult();
    }

    @Override
    public BigDecimal valorTotalNoAno() {
        Optional<BigDecimal> optional = Optional.ofNullable(
                manager.createQuery("select sum(valorTotal) from Venda where year(dataCriacao) = :ano and status = :status", BigDecimal.class)
                        .setParameter("ano", Year.now().getValue())
                        .setParameter("status", StatusVenda.EMITIDA)
                        .getSingleResult());

        return optional.orElse(BigDecimal.ZERO);
    }

	@Override
    public BigDecimal valorTotalNoMes() {
        Optional<BigDecimal> optional = Optional.ofNullable(
                manager.createQuery("select sum(valorTotal) from Venda where month(dataCriacao) = :mes and status = :status", BigDecimal.class)
                        .setParameter("mes", MonthDay.now().getMonthValue())
                        .setParameter("status", StatusVenda.EMITIDA)
                        .getSingleResult());

        return optional.orElse(BigDecimal.ZERO);
    }
    

    @Override
    public BigDecimal valorTicketMedioNoAno() {
        Optional<BigDecimal> optional = Optional.ofNullable(
                manager.createQuery("select sum(valorTotal) / count(*) from Venda where year(dataCriacao) = :ano and status = :status", BigDecimal.class)
                        .setParameter("ano", Year.now().getValue())
                        .setParameter("status", StatusVenda.EMITIDA)
                        .getSingleResult());

        return optional.orElse(BigDecimal.ZERO);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<VendaMes> totalPorMes() {
        List<VendaMes> vendasMes = manager.createNamedQuery("Vendas.totalPorMes").getResultList();

        LocalDate hoje = LocalDate.now();
        for (int i = 1; i <= 6; i++) {
            String mesIdeal = String.format("%d/%02d", hoje.getYear(), hoje.getMonthValue());

            boolean possuiMes = vendasMes.stream().filter(v -> v.getMes().equals(mesIdeal)).findAny().isPresent();
            if (!possuiMes) {
                vendasMes.add(i - 1, new VendaMes(mesIdeal, 0));
            }

            hoje = hoje.minusMonths(1);
        }
        Collections.sort(vendasMes, (VendaMes v1, VendaMes v2) -> v2.getMes().compareTo(v1.getMes()));
        return vendasMes;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<VendaOrigem> totalPorOrigem() {
        List<VendaOrigem> vendasOrigem = manager.createNamedQuery("Vendas.porOrigem").getResultList();

        LocalDate hoje = LocalDate.now();
        for (int i = 1; i <= 6; i++) {
            String mesIdeal = String.format("%d/%02d", hoje.getYear(), hoje.getMonthValue());

            boolean possuiMes = vendasOrigem.stream().filter(v -> v.getMes().equals(mesIdeal)).findAny().isPresent();
            if (!possuiMes) {
                vendasOrigem.add(i - 1, new VendaOrigem(mesIdeal, 0,0));
            }

            hoje = hoje.minusMonths(1);
        }
        Collections.sort(vendasOrigem, (VendaOrigem v1, VendaOrigem v2) -> v2.getMes().compareTo(v1.getMes()));
        return vendasOrigem;
    }

    private Long total(VendaFilter vendaFilter) {
    	CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Venda> root = query.from(Venda.class);

        List<Predicate> predicates = adicionarFiltros(vendaFilter, builder, root);
        query.where(predicates.toArray(new Predicate[0]));
        query.select(builder.count(root));

        return manager.createQuery(query).getSingleResult();
    }

    private List<Predicate> adicionarFiltros(VendaFilter vendaFilter, CriteriaBuilder builder, Root<Venda> root) {
        List<Predicate> predicates = new ArrayList<>();
            
        if (vendaFilter != null) {
        	if (vendaFilter.getCodigo() != null) {
                predicates.add(builder.equal(root.get("codigo"), vendaFilter.getCodigo()));
            }

        	if (vendaFilter.getStatusVenda() != null) {
                predicates.add(builder.equal(root.get("status"), vendaFilter.getStatusVenda()));
            }

            if (vendaFilter.getDesde() != null) {
                LocalDateTime desde = LocalDateTime.of(vendaFilter.getDesde(), LocalTime.of(0, 0));
                predicates.add(builder.equal(root.get("dataCriacao"), desde));
            }

            if (vendaFilter.getAte() != null) {
                LocalDateTime ate = LocalDateTime.of(vendaFilter.getAte(), LocalTime.of(0, 0));
                predicates.add(builder.equal(root.get("dataCriacao"), ate));
            }

            if (vendaFilter.getValorMinimo() != null) {
            	predicates.add(builder.greaterThanOrEqualTo(root.get("valorTotal"), vendaFilter.getValorMinimo()));
            }

            if (vendaFilter.getValorMaximo() != null) {       
            	predicates.add(builder.lessThanOrEqualTo(root.get("valorTotal"), vendaFilter.getValorMaximo()));
            }
            
            Join<Venda, Cliente> cliente = root.join("cliente", JoinType.LEFT);

            if (StringUtils.hasText(vendaFilter.getNomeCliente())) {
                predicates.add(builder.like(builder.lower(cliente.get("nome")),"%" + vendaFilter.getNomeCliente().toLowerCase() + "%"));
            }

            if (StringUtils.hasText(vendaFilter.getCpfOuCnpjCliente())) {
                predicates.add(builder.equal(cliente.get("cpfOuCnpj"), TipoPessoa.removerFormatacao(vendaFilter.getCpfOuCnpjCliente())));
            }
        }

        return predicates;
    }
}
