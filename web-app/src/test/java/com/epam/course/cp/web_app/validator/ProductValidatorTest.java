package com.epam.course.cp.web_app.validator;

import com.epam.course.cp.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.thymeleaf.util.StringUtils;

import static org.junit.jupiter.api.Assertions.*;

class ProductValidatorTest {

    private Product product;

    private ProductValidator productValidator = new ProductValidator();
    private BindingResult result;

    private static final String PRODUCT_NAME = "TestProduct";

    @BeforeEach
    void setUp() {

        product = Mockito.mock(Product.class);
        result = new BeanPropertyBindingResult(product, "product");
    }

    @Test
    void shouldRejectNullProductName() {

        Mockito.when(product.getProductName()).thenReturn(null);

        productValidator.validate(product, result);

        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectEmptyProductName() {

        Mockito.when(product.getProductName()).thenReturn("");

        productValidator.validate(product, result);

        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectLargeProductName() {

        Mockito.when(product.getProductName()).thenReturn(StringUtils.repeat("*", 270));

        productValidator.validate(product, result);

        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNullProductAmount() {

        Mockito.when(product.getProductName()).thenReturn(PRODUCT_NAME);
        Mockito.when(product.getProductAmount()).thenReturn(null);

        productValidator.validate(product, result);

        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectNegativeProductAmount() {

        Mockito.when(product.getProductName()).thenReturn(PRODUCT_NAME);
        Mockito.when(product.getProductAmount()).thenReturn(-10);

        productValidator.validate(product, result);

        assertTrue(result.hasErrors());
    }

    @Test
    void shouldValidateProduct() {

        Mockito.when(product.getProductName()).thenReturn(PRODUCT_NAME);
        Mockito.when(product.getProductAmount()).thenReturn(1000);

        productValidator.validate(product, result);

        assertFalse(result.hasErrors());
    }

}