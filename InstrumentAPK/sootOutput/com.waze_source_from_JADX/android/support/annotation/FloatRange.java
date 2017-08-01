package android.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
public @interface FloatRange {
    double from() throws  default Double.NEGATIVE_INFINITY;

    boolean fromInclusive() throws  default true;

    double to() throws  default Double.POSITIVE_INFINITY;

    boolean toInclusive() throws  default true;
}
