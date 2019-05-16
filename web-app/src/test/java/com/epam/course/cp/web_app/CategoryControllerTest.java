package com.epam.course.cp.web_app;

import com.epam.course.cp.dto.CategoryDTO;
import com.epam.course.cp.model.Category;
import com.epam.course.cp.service.CategoryService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:web-test.xml")
class CategoryControllerTest {

    private static final Integer ONCE = 1;

    private static final String TEST_CATEGORY_NAME = "TestCategory";
    private static final Integer PRODUCTS_AMOUNT = 2500;
    private static final Integer TEST_CATEGORY_PARENT_ID = 1;

    private static ArrayList<Category> ARRAY_LIST_OF_CATEGORIES;
    private static ArrayList<CategoryDTO> ARRAY_LIST_OF_CATEGORY_DTOS;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private CategoryService categoryService;

    @BeforeAll
    static void init() {

        ARRAY_LIST_OF_CATEGORIES = new ArrayList<>();
        ARRAY_LIST_OF_CATEGORIES.add(createCategory(1));
        ARRAY_LIST_OF_CATEGORIES.add(createCategory(2));

        ARRAY_LIST_OF_CATEGORY_DTOS = new ArrayList<>();
        ARRAY_LIST_OF_CATEGORY_DTOS.add(createCategoryDTO(2));
        ARRAY_LIST_OF_CATEGORY_DTOS.add(createCategoryDTO(3));
    }

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void findAllCategoryDTOs() throws Exception {

        Mockito.when(categoryService.findAllCategoryDTOs()).thenReturn(ARRAY_LIST_OF_CATEGORY_DTOS);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("categories"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<span class=\"text-uppercase mr-auto\">Available Categories</span>")))
        ;

        Mockito.verify(categoryService, Mockito.times(ONCE)).findAllCategoryDTOs();
    }

    @Test
    void findSubCategoriesByCategoryId() throws Exception {

        Mockito.when(categoryService.findSubCategoryDTOsByCategoryId(anyInt())).thenReturn(ARRAY_LIST_OF_CATEGORY_DTOS);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories/info/1/subs")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("subcategories-table"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<td scope=\"row\">TestCategory</td>")))
        ;

        Mockito.verify(categoryService, Mockito.times(ONCE)).findSubCategoryDTOsByCategoryId(anyInt());
    }

    @Test
    void gotoAddCategory() throws Exception {

        Mockito.when(categoryService.findAllPossibleParents()).thenReturn(ARRAY_LIST_OF_CATEGORIES);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/category")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("category"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<span class=\"text-uppercase mr-auto\">Add New Category</span>")))
        ;

        Mockito.verify(categoryService, Mockito.times(ONCE)).findAllPossibleParents();
    }

    @Test
    void addCategory() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/category")
                        .param("categoryId", "")
                        .param("categoryName", "TestCategory")
                        .param("parentId", "2")
        ).andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/categories"))
        ;
        Mockito.verify(categoryService, Mockito.times(ONCE)).add(any());
    }

    @Test
    void gotoUpdateCategory() throws Exception {

        Mockito.when(categoryService.findById(anyInt())).thenReturn(createCategory(1));
        Mockito.when(categoryService.findCategoryDTOById(anyInt())).thenReturn(createCategoryDTO(1));
        Mockito.when(categoryService.findAllPossibleParentsForId(anyInt())).thenReturn(ARRAY_LIST_OF_CATEGORIES);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/category/1")
        ).andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("category"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<p class=\"h2 text-center\">Category Details</p>")))

        ;

        Mockito.verify(categoryService, Mockito.times(ONCE)).findById(anyInt());
        Mockito.verify(categoryService, Mockito.times(ONCE)).findCategoryDTOById(anyInt());
        Mockito.verify(categoryService, Mockito.times(ONCE)).findAllPossibleParentsForId(anyInt());
    }

    @Test
    void updateCategory() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/category/1")
                        .param("categoryId", "")
                        .param("categoryName", "TestCategory")
                        .param("parentId", "2")
        ).andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/categories"))
        ;

        Mockito.verify(categoryService, Mockito.times(ONCE)).update(any());
    }

    @Test
    void deleteCategory() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/categories/1")
        ).andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/categories"))
        ;

        Mockito.verify(categoryService, Mockito.times(ONCE)).delete(any());
    }

    @AfterEach
    void afterEach() {

        Mockito.verifyNoMoreInteractions(categoryService);
        Mockito.reset(categoryService);
    }

    private static CategoryDTO createCategoryDTO(Integer id) {

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(id);
        categoryDTO.setCategoryName(TEST_CATEGORY_NAME);
        categoryDTO.setProductsAmount(PRODUCTS_AMOUNT);

        return categoryDTO;
    }

    private static Category createCategory(Integer id) {

        Category category = new Category();
        category.setCategoryId(id);
        category.setCategoryName(TEST_CATEGORY_NAME);
        category.setParentId(TEST_CATEGORY_PARENT_ID);

        return category;
    }
}