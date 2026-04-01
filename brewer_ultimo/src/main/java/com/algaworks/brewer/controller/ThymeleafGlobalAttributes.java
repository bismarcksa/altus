package com.algaworks.brewer.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ThymeleafGlobalAttributes {

	//ESSA CLASSE SERVE PARA ARCAR O CLASSE IS-ACTIVE NO MENU
    @ModelAttribute("currentUri")
    public String currentUri(HttpServletRequest request) {
        return request.getRequestURI();
    }
}