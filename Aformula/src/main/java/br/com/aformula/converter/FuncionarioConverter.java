package br.com.aformula.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.aformula.dao.FuncionarioDAO;
import br.com.aformula.domain.Funcionario;

@FacesConverter("funcionarioConverter")
public class FuncionarioConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String valor) {
		try {
			Long id = Long.parseLong(valor);
			
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			Funcionario funcionario = funcionarioDAO.buscarPorCodigo(id);
			
			return funcionario;
		} catch (RuntimeException ex) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object objeto) {
		try {
			Funcionario funcionario = (Funcionario) objeto;
			
			Long id = funcionario.getCodigo();
			return id.toString();
		} catch (RuntimeException ex) {
			return null;
		}
	}
}
