package com.epam.course.cp.rest_app;

import com.epam.course.cp.dto.CategoryDTO;
import com.epam.course.cp.model.Category;
import com.epam.course.cp.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;


@ExtendWith(MockitoExtension.class)
class CategoryRestControllerMockTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final String TEST_CATEGORY_NAME = "TestCategory";
    private static final Integer PRODUCTS_AMOUNT = 2500;
    private static final Integer TEST_CATEGORY_PARENT_ID = null;

    private static final Integer ONCE = 1;
    private static final Integer FIRST_ID = 1;
    private static final Integer SECOND_ID = 2;

    private static Category FIRST_CATEGORY;
    private static Category SECOND_CATEGORY;

    private static ArrayList<Category> ARRAY_LIST_OF_CATEGORIES;

    private static CategoryDTO FIRST_CATEGORY_DTO;
    private static CategoryDTO SECOND_CATEGORY_DTO;

    private static ArrayList<CategoryDTO> ARRAY_LIST_OF_CATEGORY_DTOS;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryRestController controller;

    private MockMvc mockMvc;

    @BeforeAll
    static void init() {

        FIRST_CATEGORY = createCategory(FIRST_ID);
        SECOND_CATEGORY = createCategory(SECOND_ID);

        ARRAY_LIST_OF_CATEGORIES = new ArrayList<>();
        ARRAY_LIST_OF_CATEGORIES.add(FIRST_CATEGORY);
        ARRAY_LIST_OF_CATEGORIES.add(SECOND_CATEGORY);

        FIRST_CATEGORY_DTO = createCategoryDTO(FIRST_ID);
        SECOND_CATEGORY_DTO = createCategoryDTO(SECOND_ID);

        ARRAY_LIST_OF_CATEGORY_DTOS = new ArrayList<>();
        ARRAY_LIST_OF_CATEGORY_DTOS.add(FIRST_CATEGORY_DTO);
        ARRAY_LIST_OF_CATEGORY_DTOS.add(SECOND_CATEGORY_DTO);

    }


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void shouldFindCategoryById() throws Exception {

        Mockito.when(categoryService.findById(anyInt())).thenReturn(SECOND_CATEGORY);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories/2")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(
                        MockMvcResultMatchers.content()
                                .string(MAPPER.writeValueAsString(SECOND_CATEGORY))
                )
        ;

        Mockito.verify(categoryService, Mockito.times(ONCE)).findById(anyInt());
    }

    @Test
    void shouldFindAllCategoryDTOs() throws Exception {

        Mockito.when(categoryService.findAllCategoryDTOs()).thenReturn(ARRAY_LIST_OF_CATEGORY_DTOS);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories/info")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(
                        MockMvcResultMatchers.content()
                                .string(MAPPER.writeValueAsString(ARRAY_LIST_OF_CATEGORY_DTOS))
                )
        ;

        Mockito.verify(categoryService, Mockito.times(ONCE)).findAllCategoryDTOs();
    }

    @Test
    void shouldFindSubCategoryDTOsByCategoryId() throws Exception {

        Mockito.when(categoryService.findSubCategoryDTOsByCategoryId(anyInt()))
                .thenReturn(ARRAY_LIST_OF_CATEGORY_DTOS);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories/info/1/subs")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(
                        MockMvcResultMatchers.content()
                                .string(MAPPER.writeValueAsString(ARRAY_LIST_OF_CATEGORY_DTOS))
                )
        ;

        Mockito.verify(categoryService, Mockito.times(ONCE)).findSubCategoryDTOsByCategoryId(anyInt());
    }

    @Test
    void shouldAddCategory() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(MAPPER.writeValueAsString(FIRST_CATEGORY))
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(categoryService, Mockito.times(ONCE)).add(any());
    }

    @Test
    void shouldUpdateCategory() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.put("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(MAPPER.writeValueAsString(FIRST_CATEGORY))
        ).andExpect(MockMvcResultMatchers.status().isOk())
        ;

        Mockito.verify(categoryService, Mockito.times(ONCE)).update(any());
    }

    @Test
    void shouldDeleteCategory() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/categories/1")
        ).andExpect(MockMvcResultMatchers.status().isOk())
        ;

        Mockito.verify(categoryService, Mockito.times(ONCE)).delete(anyInt());
    }

    @Test
    void shouldFindAllSubCategories() throws Exception {

        Mockito.when(categoryService.findAllSubCategories()).thenReturn(ARRAY_LIST_OF_CATEGORIES);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories/subs")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content()
                                .string(MAPPER.writeValueAsString(ARRAY_LIST_OF_CATEGORIES))
                )
        ;

        Mockito.verify(categoryService, Mockito.times(ONCE)).findAllSubCategories();
    }

    @Test
    void shouldFindCategoryDTOById() throws Exception {

        Mockito.when(categoryService.findCategoryDTOById(anyInt())).thenReturn(FIRST_CATEGORY_DTO);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories/info/1")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content()
                                .string(MAPPER.writeValueAsString(FIRST_CATEGORY_DTO))
                )
        ;

        Mockito.verify(categoryService, Mockito.times(ONCE)).findCategoryDTOById(anyInt());
    }

    @Test
    void shouldFindAllPossibleParents() throws Exception {

        Mockito.when(categoryService.findAllPossibleParents()).thenReturn(ARRAY_LIST_OF_CATEGORIES);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories/possibleparents")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content()
                                .string(MAPPER.writeValueAsString(ARRAY_LIST_OF_CATEGORIES))
                )
        ;

        Mockito.verify(categoryService, Mockito.times(ONCE)).findAllPossibleParents();
    }

    @Test
    void shouldFindAllPossibleParentsForId() throws Exception {

        Mockito.when(categoryService.findAllPossibleParentsForId(anyInt())).thenReturn(ARRAY_LIST_OF_CATEGORIES);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories/possibleparents/1")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.content()
                                .string(MAPPER.writeValueAsString(ARRAY_LIST_OF_CATEGORIES))
                )
        ;

        Mockito.verify(categoryService, Mockito.times(ONCE)).findAllPossibleParentsForId(anyInt());
    }

    @AfterEach
    void afterEach() {

        Mockito.verifyNoMoreInteractions(categoryService);
    }

    private static Category createCategory(Integer id) {

        Category category = new Category();
        category.setCategoryId(id);
        category.setCategoryName(TEST_CATEGORY_NAME + id);
        category.setParentId(TEST_CATEGORY_PARENT_ID);

        return category;
    }

    private static CategoryDTO createCategoryDTO(Integer id) {

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(id);
        categoryDTO.setCategoryName(TEST_CATEGORY_NAME + id);
        categoryDTO.setProductsAmount(PRODUCTS_AMOUNT);

        return categoryDTO;
    }
}