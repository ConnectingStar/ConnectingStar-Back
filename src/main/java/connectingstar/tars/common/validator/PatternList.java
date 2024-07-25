package connectingstar.tars.common.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * list의 각 값이 정규식을 만족하는지 검사하는 validator annotation
 *
 * @author 이우진
 * @see jakarta.validation.constraints.Pattern
 */
@Documented
@Constraint(validatedBy = PatternListValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PatternList {
    String message() default "Invalid list values";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regexp();
}