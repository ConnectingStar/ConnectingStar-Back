package connectingstar.tars.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.regex.Pattern;

/**
 * annotation @PatternList 의 validator
 *
 * @author 이우진
 * @see connectingstar.tars.common.validator.PatternList
 */
public class PatternListValidator implements ConstraintValidator<PatternList, List<String>> {

    private Pattern pattern;

    @Override
    public void initialize(PatternList constraintAnnotation) {
        pattern = Pattern.compile(constraintAnnotation.regexp());
    }

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (values == null) {
            return true; // null values are considered valid
        }

        for (String value : values) {
            if (value != null && !pattern.matcher(value).matches()) {
                return false;
            }
        }

        return true;
    }
}
