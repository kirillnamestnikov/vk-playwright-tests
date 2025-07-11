package tags.pages;

import org.junit.jupiter.api.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Tag("Media")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MediaTag{

}