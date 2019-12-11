package br.com.hbsis.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidatorCNPJ.class)
@Documented
public @interface ValidationCNPJ {

    String message() default "";

    long length() default -1L;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
