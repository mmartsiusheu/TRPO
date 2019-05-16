package com.epam.course.cp.rest_app.handler;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class RestErrorHandlerTest {

    private ExceptionThrowingTestController controller;

    private RestErrorHandler errorHandler;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        controller = new ExceptionThrowingTestController();

        errorHandler = new RestErrorHandler();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .setControllerAdvice(errorHandler)
                .build();
    }

    @Test
    void shouldHandleDaoRuntimeException() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/exceptions/daoRuntimeException")
        ).andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Dao runtime exception")))
        ;
    }

    @Test
    void shouldHandleEmptyResultDataAccessException() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/exceptions/emptyResultDataAccessException")
        ).andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Incorrect result size: expected 0, actual 0")))
        ;
    }

    @Test
    void shouldHandleDuplicateKeyException() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/exceptions/duplicateKeyException")
        ).andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("This instance is already exist")))
        ;
    }
}