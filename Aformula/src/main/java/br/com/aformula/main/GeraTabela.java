package br.com.aformula.main;

import br.com.aformula.util.HibernateUtil;

public class GeraTabela {

	public static void main(String[] args) {
		
		HibernateUtil.getSessionFactory();

		HibernateUtil.getSessionFactory().close();
	}

}
