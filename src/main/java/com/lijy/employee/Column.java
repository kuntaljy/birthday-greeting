package com.lijy.employee;

import java.lang.annotation.*;

/**
 * @author lijy
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    String value();
    String format() default "";
}
