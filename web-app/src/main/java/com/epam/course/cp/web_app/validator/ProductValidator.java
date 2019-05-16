package com.epam.course.cp.web_app.validator;

import com.epam.course.cp.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductValidator implements Validator {

    private static final Integer MAX_STRING_LENGTH = 255;

    @Override
    public boolean supports(Class<?> aClass) {
        return Product.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "productName", "product.emptyName");

        Product product = (Product) o;
        if (StringUtils.hasLength(product.getProductName())
                && product.getProductName().length() > MAX_STRING_LENGTH) {
            errors.rejectValue("productName", "product.max255");
        }
        if (product.getProductAmount() == null) {
            errors.rejectValue("productAmount", "product.emptyAmount");
        } else {
            if (product.getProductAmount() < 0) {
                errors.rejectValue("productAmount", "product.negativeAmount");
            }
        }
    }
}
