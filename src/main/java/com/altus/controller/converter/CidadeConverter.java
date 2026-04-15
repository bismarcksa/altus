package com.altus.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.altus.model.Cidade;

public class CidadeConverter implements Converter<String, Cidade>{

	@SuppressWarnings("deprecation")
	@Override
    public Cidade convert(String codigo) {
        if(!StringUtils.isEmpty(codigo)){
	        	Cidade cidade = new Cidade();
	        	cidade.setCodigo(Long.valueOf(codigo));
	        return cidade;
        }
        
        return null;
    }
}
