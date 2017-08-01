package android.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.CLASS)
public @interface RequiresPermission {

    @Target({ElementType.FIELD})
    public @interface Read {
        RequiresPermission value() throws ;
    }

    @Target({ElementType.FIELD})
    public @interface Write {
        RequiresPermission value() throws ;
    }

    String[] allOf() throws  default {};

    String[] anyOf() throws  default {};

    boolean conditional() throws  default false;

    String value() throws  default "";
}
