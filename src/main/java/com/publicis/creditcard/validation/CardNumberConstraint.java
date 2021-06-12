package com.publicis.creditcard.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CardNumberValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CardNumberConstraint {

        String message() default "Sequence is Not a Valid Card Number.";
        Class<?>[] groups() default {};
        Class<? extends Payload>[] payload() default {};

}
