package com.altus.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;
import com.altus.model.Departamento;

public class DepartamentoConverter implements Converter<String, Departamento>{

    @SuppressWarnings("deprecation")
	@Override
    public Departamento convert(String id) {
        if(!StringUtils.isEmpty(id)){
        	Departamento departamento = new Departamento();
        	departamento.setId(Long.valueOf(id));
            return departamento;
        }        
        return null;
    }
}
