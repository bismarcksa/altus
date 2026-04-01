package com.algaworks.brewer.repository.helper.usuario;

import com.algaworks.brewer.model.Grupo;
import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.filter.UsuarioFilter;
import com.algaworks.brewer.repository.paginacao.PaginacaoUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepositoryImpl implements UsuarioRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private PaginacaoUtil paginacaoUtil;

    @Override
    public Optional<Usuario> porEmailEAtivo(String email) {
        return manager
                .createQuery("from Usuario where lower(email) = lower(:email) and ativo = true", Usuario.class)
                .setParameter("email", email).getResultList().stream().findFirst();
    }

    @Override
    public List<String> permissoes(Usuario usuario) {
        return manager.createQuery("select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario", String.class)
                .setParameter("usuario", usuario)
                .getResultList();
    }
    
    @Transactional(readOnly = true)
    public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
        Root<Usuario> root = criteria.from(Usuario.class);

        // FETCH JOIN
        root.fetch("grupos", JoinType.LEFT); // Força o fetch do grupos para evitar N+1, Fetch join apenas para a listagem (não afeta a contagem)

        criteria.select(root).distinct(true);

        List<Predicate> predicates = adicionarFiltros(filtro, builder, root);
        criteria.where(predicates.toArray(new Predicate[0]));       
        
        // Cria query já configurada
        TypedQuery<Usuario> query = manager.createQuery(criteria);
        
        paginacaoUtil.prepararPaginacao(query, pageable);
        
        //VOU VER SE PODE TIRAR
//        adicionarFiltros(filtro, builder, root, predicates);
          
        List<Usuario> usuarios = query.getResultList();
        Long total = total(filtro);
        
        return new PageImpl<>(usuarios, pageable, total); 
    } 
    
    @Override
    @Transactional(readOnly = true)
    public Usuario buscarUsuarioComGrupos(Long codigo) {

        return manager.createQuery(
            "select u from Usuario u left join fetch u.grupos where u.codigo = :codigo",
            Usuario.class)
            .setParameter("codigo", codigo)
            .getSingleResult();
    }
  
    private Long total(UsuarioFilter filtro) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<Usuario> root = query.from(Usuario.class);

        List<Predicate> predicates = adicionarFiltros(filtro, builder, root);
        query.where(predicates.toArray(new Predicate[0]));
        query.select(builder.count(root));

        return manager.createQuery(query).getSingleResult();
    }
    
    private List<Predicate> adicionarFiltros(UsuarioFilter filtro, CriteriaBuilder builder, Root<Usuario> root) {
    	List<Predicate> predicates = new ArrayList<>();
    	if (filtro != null) {  		
			if (StringUtils.hasText(filtro.getNome())) {
				predicates.add(builder.like(builder.lower(root.get("nome")),"%" + filtro.getNome().toLowerCase() + "%"));
			}
	
			if (StringUtils.hasText(filtro.getEmail())) {
				predicates.add(builder.like(builder.lower(root.get("email")),"%" + filtro.getEmail().toLowerCase() + "%"));
			}
	
			if (filtro.getGrupos() != null && !filtro.getGrupos().isEmpty()) {
			
				Join<Usuario, Grupo> joinGrupo = root.join("grupos", JoinType.LEFT);
				predicates.add(joinGrupo.get("codigo").in(filtro.getGrupos().stream().map(Grupo::getCodigo).toList()));
			}
    	}
		
		return predicates;
	}
}

