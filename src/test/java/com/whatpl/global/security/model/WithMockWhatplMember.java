package com.whatpl.global.security.model;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockWhatplMemberSecurityContextFactory.class)
public @interface WithMockWhatplMember {

    @AliasFor("value")
    long id() default 1L;

    boolean hasProfile() default true;

    @AliasFor("id")
    long value() default 1L;
}
