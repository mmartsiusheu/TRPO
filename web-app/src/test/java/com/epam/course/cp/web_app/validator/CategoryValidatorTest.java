package com.epam.course.cp.web_app.validator;

import com.epam.course.cp.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.thymeleaf.util.StringUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryValidatorTest {

    private Category category;

    private CategoryValidator categoryValidator = new CategoryValidator();
    private BindingResult result;

    @BeforeEach
    void setUp() {

        category = Mockito.mock(Category.class);
        result = new BeanPropertyBindingResult(category, "category");
    }

    @Test
    void shouldRejectNullCategoryName() {

        Mockito.when(category.getCategoryName()).thenReturn(null);

        categoryValidator.validate(category, result);

        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectEmptyCategoryName() {

        Mockito.when(category.getCategoryName()).thenReturn("");

        categoryValidator.validate(category, result);

        assertTrue(result.hasErrors());
    }

    @Test
    void shouldRejectLargeCategoryName() {

        Mockito.when(category.getCategoryName()).thenReturn(StringUtils.repeat("8", 270));

        categoryValidator.validate(category, result);

        assertTrue(result.hasErrors());
    }


    @Test
    void shouldValidateCategoryName() {

        Mockito.when(category.getCategoryName()).thenReturn(StringUtils.repeat("8", 250));

        categoryValidator.validate(category, result);

        assertFalse(result.hasErrors());
    }
}