package connectingstar.tars.common.validator;

import connectingstar.tars.common.enums.Codable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValueOfCodableEnumValidator implements ConstraintValidator<ValueOfCodableEnum, CharSequence> {
    private List<String> acceptedValues;

    @Override
    public void initialize(ValueOfCodableEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(enumConstant -> ((Codable) enumConstant).getCode())
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return acceptedValues.contains(value.toString());
    }
}