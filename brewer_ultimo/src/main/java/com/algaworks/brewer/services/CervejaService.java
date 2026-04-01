package com.algaworks.brewer.services;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.CervejaRepository;
import com.algaworks.brewer.services.exception.ImpossivelExcluirEntidadeException;
import com.algaworks.brewer.storage.FotoStorage;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CervejaService {

    @Autowired
    private CervejaRepository cervejaRepository;
    
    @Autowired
    private FotoStorage fotoStorage;

    @Transactional
    public void salvar(Cerveja cerveja){
        cervejaRepository.save(cerveja);

    }
    
    @Transactional
    public void excluir(Cerveja cerveja) {
        try {
            String foto = cerveja.getFoto();
            cervejaRepository.delete(cerveja);
            cervejaRepository.flush();
            fotoStorage.excluir(foto);
        } catch (DataIntegrityViolationException | PersistenceException e) {
            throw new ImpossivelExcluirEntidadeException(
                "Impossível excluir a cerveja. Já foi utilizada em uma venda");
        }
    }

}
