package tj.ebm.Commons.Validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UniqueLoginValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueLogin {

	String message() default "User's login must be unique! Given login is reserved";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
