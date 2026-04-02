package com.altus.config.init;
/*
import java.util.EnumSet;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.SessionTrackingMode;


public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

   @Override
	protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
//	   servletContext.getSessionCookieConfig().setMaxAge(20); //TEMPO DE SESSÃO EM SEGUNDOS
	   servletContext.setSessionTrackingModes(EnumSet.of(SessionTrackingMode.COOKIE));
	   
		FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		characterEncodingFilter.setInitParameter("forceEncoding", "true");
		characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
	}
}*/
