package com.epam.course.cp.web_app.validator;

import com.epam.course.cp.dto.Filter;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FilterValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Filter.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Filter filter = (Filter) o;
        if (isNotEmptyDateFields(filter)
                && filter.getDateBegin().isAfter(filter.getDateEnd())) {
            errors.rejectValue("dateBegin", "filter.wrongDates");
        }
    }

    private boolean isNotEmptyDateFields(Filter filter) {
        return filter.getDateBegin() != null && filter.getDateEnd() != null;
    }
}
