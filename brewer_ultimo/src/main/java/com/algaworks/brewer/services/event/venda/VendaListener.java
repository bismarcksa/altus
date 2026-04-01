package com.algaworks.brewer.services.event.venda;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.ItemVenda;
import com.algaworks.brewer.repository.CervejaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class VendaListener {

    @Autowired
    private CervejaRepository cervejaRepository;

    @EventListener
    public void vendaEmitida(VendaEvent vendaEvent) {
        for (ItemVenda item : vendaEvent.getVenda().getItens()) {
            @SuppressWarnings("deprecation")
			Cerveja cerveja = cervejaRepository.getOne(item.getCerveja().getCodigo());

            cerveja.setQuantidadeEstoque(cerveja.getQuantidadeEstoque() - item.getQuantidade());
            cervejaRepository.save(cerveja);
        }
    }
}
