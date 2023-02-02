package com.hoaxify.hoaxifyspringboot.api.defaults.annotations;


import com.hoaxify.hoaxifyspringboot.api.defaults.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {UniqueUsernameValidator.class})
public @interface UniqueUsername {
    String message() default "Username must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
