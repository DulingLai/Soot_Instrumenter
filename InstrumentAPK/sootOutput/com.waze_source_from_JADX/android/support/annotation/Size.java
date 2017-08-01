package android.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
public @interface Size {
    long max() throws  default Long.MAX_VALUE;

    long min() throws  default Long.MIN_VALUE;

    long multiple() throws  default 1;

    long value() throws  default -1;
}
