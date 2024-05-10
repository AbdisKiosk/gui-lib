package me.abdiskiosk.guis.reflection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Placeholder {
    String name() default "";
}
