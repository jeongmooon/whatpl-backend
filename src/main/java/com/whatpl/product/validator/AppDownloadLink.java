package com.whatpl.product.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Pattern(regexp = AppDownloadLink.AppDownloadLinkPattern)
public @interface AppDownloadLink {
    String AppDownloadLinkPattern = "((https://)?(play\\.google\\.com|apps\\.apple\\.com|testflight\\.apple\\.com)/.*\\bid\\d{9}\\b)";
    String message() default "유효하지 않은 다운로드 링크입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
