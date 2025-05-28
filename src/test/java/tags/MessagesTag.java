package tags;

import org.junit.jupiter.api.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Test
@Tag("Messages")
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessagesTag{

}