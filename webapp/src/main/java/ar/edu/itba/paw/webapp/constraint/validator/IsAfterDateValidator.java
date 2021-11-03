package ar.edu.itba.paw.webapp.constraint.validator;

import ar.edu.itba.paw.webapp.constraint.annotation.IsAfterDate;
import org.springframework.cglib.core.Local;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class IsAfterDateValidator implements ConstraintValidator<IsAfterDate, Object> {

    private String startDate;
    private String endDate;

    public void initialize(IsAfterDate constraintAnnotation) {
        this.startDate = constraintAnnotation.startDate();
        this.endDate = constraintAnnotation.endDate();

    }

    public boolean isValid(Object value, ConstraintValidatorContext constraintContext) {
        try
        {
//            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
//            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);
//
//            return firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        }
        catch (final Exception ignore)
        {
            // ignore
        }
        if (startDate == null || startDate.equals("") || endDate==null || endDate.equals("")){
            return true;
        }
        return LocalDateTime.parse(endDate).isAfter(LocalDateTime.parse(startDate));
    }

}