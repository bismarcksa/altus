package com.altus.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.altus.model.Cidade;

public class CidadeConverter implements Converter<String, Cidade>{

	@SuppressWarnings("deprecation")
	@Override
    public Cidade convert(String id) {
        if(!StringUtils.isEmpty(id)){
	        	Cidade cidade = new Cidade();
	        	cidade.setId(Long.valueOf(id));
	        return cidade;
        }
        
        return null;
    }
}
