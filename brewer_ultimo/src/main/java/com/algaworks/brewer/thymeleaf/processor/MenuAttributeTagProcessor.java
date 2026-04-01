package com.algaworks.brewer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class MenuAttributeTagProcessor extends AbstractAttributeTagProcessor {

    private static final String NOME_ATTRIBUTO = "menu";
    private static final int PRECEDENCIA = 1000;

    public MenuAttributeTagProcessor(String dialectPrefix) {
        super(TemplateMode.HTML,dialectPrefix, null, false, NOME_ATTRIBUTO, true, PRECEDENCIA, true);
    }

    @Override
    protected void doProcess(ITemplateContext context, IProcessableElementTag tag, AttributeName attributeName, String attributeValue, IElementTagStructureHandler structureHandler) {

        Object currentUriObj = context.getVariable("currentUri");
        
        if (currentUriObj == null) {
            return;
        }

        String currentUri = currentUriObj.toString();            
        String menu = attributeValue;
         
        if (currentUri.contains(menu)) {
            String classes = tag.getAttributeValue("class");
            if (classes == null) {
                classes = "";
            }

            structureHandler.setAttribute("class",(classes + " is-active").trim());
        }
    }



}
