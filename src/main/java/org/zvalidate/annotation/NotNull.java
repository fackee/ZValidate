package org.zvalidate.annotation;

import java.lang.annotation.*;

/**
 * Created by xz on 2017/8/27.
 */

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotNull {

    String value() default "";
}
