package com.epam.course.cp.web_app.handler;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:web-test.xml")
class WebErrorHandlerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void shouldHandleHttpClientErrorException() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/httpClientException")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("exception"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<p class=\"h3 text-danger\">Oopss..! Something goes wrong!</p>")))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<p>400 Bad request</p>")))
        ;
    }

    @Test
    void shouldHandleHttpServerErrorException() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/httpServerException")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("exception"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<p class=\"h3 text-danger\">Oopss..! Something goes wrong!</p>")))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<p>500 Internal server error</p>")))
        ;
    }

    @Test
    void shouldHandleResourceAccessException() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/resourceAccessException")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("no_source"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("We are sorry, but we have lost a connection to our server")))
        ;
    }
}