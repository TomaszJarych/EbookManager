package tj.ebm.commons.ErrorsUtil;

import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class ErrorsUtil {

    public static List<String> errorsToListStringFromFieldErrors(
            List<FieldError> errors) {
        return errors.stream()
                .map(el -> el.getField() + " : " + el.getDefaultMessage())
                .collect(Collectors.toList());
    }

    public static String errorsToStringFromFieldErrors(
            List<FieldError> errors) {
        return errors.stream()
                .map(el -> el.getField() + " : " + el.getDefaultMessage())
                .collect(Collectors.joining(";"));
    }

}
