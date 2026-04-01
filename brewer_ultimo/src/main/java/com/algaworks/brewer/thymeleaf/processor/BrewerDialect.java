package com.algaworks.brewer.thymeleaf.processor;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.HashSet;
import java.util.Set;

@Component
public class BrewerDialect extends AbstractProcessorDialect{

    public BrewerDialect() {
        super("Brewer Dialect", "brewer", StandardDialect.PROCESSOR_PRECEDENCE);
//        System.out.println(">>> BrewerDialect CARREGADO PELO SPRING");
    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final Set<IProcessor> processadores = new HashSet<>();
        processadores.add(new ClassForError(dialectPrefix));
        processadores.add(new MessageElementTagProcessor(dialectPrefix));
        processadores.add(new OrderElementTagProcessor(dialectPrefix));
        processadores.add(new PaginationElementTagProcessor(dialectPrefix));
        processadores.add(new MenuAttributeTagProcessor(dialectPrefix));
        return processadores;
    }
}
