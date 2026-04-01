package com.algaworks.brewer.repository.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

//import com.algaworks.brewer.BrewerApplication;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.storage.FotoStorage;

import jakarta.persistence.PostLoad;

public class CervejaEntityListener {

	@Autowired
	private FotoStorage fotoStorage;
	
    @PostLoad
    public void postLoad(final Cerveja cerveja){
        //FotoStorage fotoStorage = BrewerApplication.getBean(FotoStorage.class);
    		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        cerveja.setUrlFoto(fotoStorage.getUrl(cerveja.getFotoOuMock()));
        cerveja.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + cerveja.getFotoOuMock()));
    }
}
