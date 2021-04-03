package bg.softuni.hotelagency.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReservationDatesValidator.class)
public @interface ReservationDates {

    String message() default "Arrive date must be earlier that leave date";

    String first();

    String second();


    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
