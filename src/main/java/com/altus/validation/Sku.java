package com.altus.validation;

import jakarta.validation.Constraint;
import jakarta.validation.OverridesAttribute;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = "([a-zA-Z]{2}\\d{4})?")
public @interface Sku {

    @OverridesAttribute(constraint = Pattern.class, name = "message")
    String message() default "Formato do SkU inválido, o padrão deve ser XX9999";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
