package com.ypy.pyojbackend.aop;

import com.ypy.pyojbackend.model.enums.UserRoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * \\ using example
 * \\ @AuthCheck(mustRole = UserRoleEnum.ADMIN)
 * \\ @PostMapping("/question/create")
 */
@Target({ElementType.METHOD, ElementType.TYPE})  // allow annotation work both on method and class(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthCheck {

    UserRoleEnum mustRole() default UserRoleEnum.USER;
}
