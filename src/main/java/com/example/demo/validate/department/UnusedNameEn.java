package com.example.demo.validate.department;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = UnusedNameEnValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface UnusedNameEn {

	// エラーメッセージを設定
	String message();
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
 
    @Target({FIELD})
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        UnusedNameEn[] value(); 
    }

}