package br.com.aformula.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.aformula.dao.CargoDAO;
import br.com.aformula.domain.Cargo;

@FacesConverter("cargoConverter")
public class CargoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String valor) {
		try {
			Long id = Long.parseLong(valor);
			
			CargoDAO cargoDAO = new CargoDAO();
			Cargo cargo = cargoDAO.buscarPorCodigo(id);
			
			return cargo;
		} catch (RuntimeException ex) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object objeto) {
		try {
			Cargo cargo = (Cargo) objeto;
			
			Integer id = cargo.getCodigo();
			return id.toString();
		} catch (RuntimeException ex) {
			return null;
		}
	}

}
