package com.altus.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;
import com.altus.model.Estado;

public class EstadoConverter implements Converter<String, Estado>{

    @SuppressWarnings("deprecation")
	@Override
    public Estado convert(String id) {
        if(!StringUtils.isEmpty(id)){
        	Estado estado = new Estado();
        	estado.setId(Long.valueOf(id));
            return estado;
        }
        
        return null;
    }
}
