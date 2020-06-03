package space.springboot.community.aspect;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HyperLogInc {

    String description() default "";
}
