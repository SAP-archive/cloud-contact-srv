package com.sap.hana.cloud.samples.contactsrv.xcc.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = ValidPhoneNumberValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidPhoneNumber
{
	String message() default "phone_numer.number.validity.error";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
