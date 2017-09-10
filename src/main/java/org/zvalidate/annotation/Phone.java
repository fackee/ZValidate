package org.zvalidate.annotation;

import java.lang.annotation.*;

/**
 * Created by xz on 2017/8/27.
 */

@Inherited
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Phone {

    String value() default "";

    String county() default "";

    String province() default "";

    String city() default "";

}
