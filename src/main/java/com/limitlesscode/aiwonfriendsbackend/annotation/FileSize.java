package com.limitlesscode.aiwonfriendsbackend.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileSizeValidator.class)
public @interface FileSize {
    String message() default "파일 용량을 5MB 이하로 업로드 바랍니다.";

    int maxSizeKb() default 5 * 1024 ;

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
