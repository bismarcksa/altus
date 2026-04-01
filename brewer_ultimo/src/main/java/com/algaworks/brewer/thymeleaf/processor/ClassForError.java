package com.algaworks.brewer.thymeleaf.processor;


import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring6.util.FieldUtils;
import org.thymeleaf.templatemode.TemplateMode;

public class ClassForError extends AbstractAttributeTagProcessor{

    private static final String NOME_ATTRIBUTE = "classforerror";
    private static final int PRECEDENCIA = 1000;

    public ClassForError(String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, null, false, NOME_ATTRIBUTE, true, PRECEDENCIA, true);
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValue, IElementTagStructureHandler structureHandler) {

        boolean temErro = FieldUtils.hasErrors(context, attributeValue);

        if(temErro){
            String classesExistentes = tag.getAttributeValue("class");
            
            if (classesExistentes == null) {
                classesExistentes = "";
            }
            
            structureHandler.setAttribute("class", classesExistentes + " has-error");
        }
    }
}
