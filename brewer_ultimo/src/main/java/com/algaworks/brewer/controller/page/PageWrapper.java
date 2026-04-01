package com.algaworks.brewer.controller.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.util.UriComponentsBuilder;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public class PageWrapper<T> {

    private Page<T> page;
    private UriComponentsBuilder builder;

    public PageWrapper(Page<T> page, HttpServletRequest httpServletRequest) {
        this.page = page;
        //this.builder = ServletUriComponentsBuilder.fromRequest(httpServletRequest);
        String httpUrl = httpServletRequest.getRequestURL().append(httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "")
        		.toString().replaceAll("\\+", "%20").replace("excluido", "");
        this.builder = UriComponentsBuilder.fromHttpUrl(httpUrl);
    }

    public List<T> getConteudo(){
        return page.getContent();
    }

    public boolean isVazia(){
        return page.getContent().isEmpty();
    }

    public int getAtual(){
        return page.getNumber();
    }

    public boolean isPrimeira(){
        return page.isFirst();
    }

    public boolean isUltima(){
        return page.isLast();
    }

    public int getTotal(){
        return page.getTotalPages();
    }

    public boolean getUnicaPagina(){
        return getTotal() < 2;
    }

    public String urlParaPagina(int pagina){
        return builder.replaceQueryParam("page", pagina).build(true).encode().toUriString();
    }

    public String urlOrdenada(String propriedade){
        UriComponentsBuilder uriBuilderOrder = UriComponentsBuilder.fromUriString(builder.build(true).encode().toUriString());

        String valorSort = String.format("%s,%s", propriedade, inverterDirecao(propriedade));

        return uriBuilderOrder.replaceQueryParam("sort", valorSort).build(true).encode().toUriString();
    }

    public String inverterDirecao(String propriedade) {
        String direcao = "asc";
        Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null;
        if(order != null){
//            direcao = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc";
            direcao = order.isAscending() ? "desc" : "asc";
        }
        return direcao;
    }

    public boolean descendente(String propriedade){
        return inverterDirecao(propriedade).equals("asc");
    }

    public boolean ordenada(String propriedade){
        Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null;
        return order != null;
    }

}
