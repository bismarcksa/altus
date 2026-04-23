package com.altus.config;

import com.altus.controller.converter.CidadeConverter;
import com.altus.controller.converter.DepartamentoConverter;
import com.github.benmanes.caffeine.cache.Caffeine;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@Configuration
@EnableCaching
@EnableAsync
public class WebConfig implements WebMvcConfigurer{

    @Bean
    public LocaleResolver localeResolver() {
        return new FixedLocaleResolver(new Locale("pt", "BR"));
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
        bundle.setBasename("classpath:/messages");
        bundle.setDefaultEncoding("UTF-8");
        return bundle;
    }
    
    @Bean // ANTES ERA NO TEMPLATEENGINE
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
    
    //TODOS OS RECURSOS STATICOS VAI PROCURAR NESSE CAMINHO
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }
    
    @Override //TRABALHA COM MEUS CONVERSORES
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CidadeConverter());
        registry.addConverter(new DepartamentoConverter());
    }

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(
            Caffeine.newBuilder()
                .expireAfterAccess(20, TimeUnit.MINUTES)
                .maximumSize(3)
        );
        return cacheManager;
    }
}