package com.limitlesscode.aiwonfriendsbackend.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageValidator.class)
public @interface Image {

    String message() default "유효하지 않은 이미지 파일입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
