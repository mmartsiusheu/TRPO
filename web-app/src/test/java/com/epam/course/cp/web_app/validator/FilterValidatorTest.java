package com.epam.course.cp.web_app.validator;

import com.epam.course.cp.dto.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilterValidatorTest {

    private Filter filter;

    private FilterValidator filterValidator = new FilterValidator();
    private BindingResult result;

    @BeforeEach
    void setUp() {

        filter = Mockito.mock(Filter.class);
        result = new BeanPropertyBindingResult(filter, "filter");
    }

    @Test
    void shouldRejectWrongDateInterval() {

        Mockito.when(filter.getDateBegin()).thenReturn(LocalDate.parse("2100-01-01"));
        Mockito.when(filter.getDateEnd()).thenReturn(LocalDate.parse("1990-01-01"));

        filterValidator.validate(filter, result);

        assertTrue(result.hasErrors());
    }

    @Test
    void shouldValidateDateInterval() {

        Mockito.when(filter.getDateBegin()).thenReturn(LocalDate.parse("2018-01-01"));
        Mockito.when(filter.getDateEnd()).thenReturn(LocalDate.parse("2020-01-01"));

        filterValidator.validate(filter, result);

        assertFalse(result.hasErrors());
    }
}