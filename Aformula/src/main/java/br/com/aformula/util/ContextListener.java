package br.com.aformula.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
	
	//QUANDO FINALIZA O TOMCAT
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		HibernateUtil.getSessionFactory().close();
	}
	
	//QUANDO INICIA O TOMCAT 
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//Vai Perceber que o Hibernate não Foi inicializado e criará uma Sess�o.
		HibernateUtil.getSessionFactory().openSession();
	}
	
}
