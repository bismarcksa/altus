package br.com.aformula.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import br.com.aformula.dao.GestaoPerformanceDAO;
import br.com.aformula.domain.GestaoPerformance;

@FacesConverter("gestaoPerformanceConverter")
public class GestaoPerformanceConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String valor) {
		try {
			Long id = Long.parseLong(valor);
			
			GestaoPerformanceDAO gestaoPerformanceDAO = new GestaoPerformanceDAO();
			GestaoPerformance gestaoPerformance = gestaoPerformanceDAO.buscarPorCodigo(id);
			
			return gestaoPerformance;
		} catch (RuntimeException ex) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object objeto) {
		try {
			GestaoPerformance gestaoPerformance = (GestaoPerformance) objeto;
			
			Integer id = gestaoPerformance.getCodigo();
			return id.toString();
		} catch (RuntimeException ex) {
			return null;
		}
	}

}
