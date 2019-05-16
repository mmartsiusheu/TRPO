package com.epam.course.cp.web_app.consumer;

import com.epam.course.cp.dto.CategoryDTO;
import com.epam.course.cp.model.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CategoryRestConsumerMockTest {

    private static final Integer ONCE = 1;

    private static final String CATEGORY_NAME = "TestCategory";
    private static final Integer CATEGORY_PARENT_ID = 1;
    private static final Integer CATEGORY_PRODUCT_AMOUNT = 2500;

    private static final Integer FIRST_CATEGORY_ID = 2;

    private static Category FIRST_CATEGORY;

    private static CategoryDTO FIRST_CATEGORY_DTO;

    private static List<Category> ARRAY_LIST_OF_CATEGORY;

    private static List<CategoryDTO> ARRAY_LIST_OF_CATEGORY_DTOS;

    private RestTemplate restTemplate;

    private String url = "/rest/categories";

    private CategoryRestConsumer categoryRestConsumer;

    @BeforeAll
    static void init() {

        FIRST_CATEGORY = createCategory(FIRST_CATEGORY_ID);

        FIRST_CATEGORY_DTO = createCategoryDTO(FIRST_CATEGORY_ID);

        ARRAY_LIST_OF_CATEGORY_DTOS = new ArrayList<>();
        ARRAY_LIST_OF_CATEGORY_DTOS.add(createCategoryDTO(2));
        ARRAY_LIST_OF_CATEGORY_DTOS.add(createCategoryDTO(3));

        ARRAY_LIST_OF_CATEGORY = new ArrayList<>();
        ARRAY_LIST_OF_CATEGORY.add(createCategory(2));
        ARRAY_LIST_OF_CATEGORY.add(createCategory(3));
    }

    @BeforeEach
    void setUp() {

        restTemplate = Mockito.mock(RestTemplate.class);
        categoryRestConsumer = new CategoryRestConsumer(url, restTemplate);
    }

    @Test
    void shouldFindCategoryById() {

        Mockito.when(restTemplate.getForEntity(url + "/" + FIRST_CATEGORY_ID, Category.class))
                .thenReturn(new ResponseEntity<>(FIRST_CATEGORY, HttpStatus.OK));

        Category category = categoryRestConsumer.findById(FIRST_CATEGORY_ID);

        assertNotNull(category);
        assertEquals(FIRST_CATEGORY, category);

        Mockito.verify(restTemplate, Mockito.times(ONCE))
                .getForEntity(url + "/" + FIRST_CATEGORY_ID, Category.class);
    }

    @Test
    void shouldFindAllCategoryDTOs() {

        Mockito.when(restTemplate.getForEntity(url + "/info", List.class))
                .thenReturn(new ResponseEntity<>(ARRAY_LIST_OF_CATEGORY_DTOS, HttpStatus.OK));

        List<CategoryDTO> categoryDTOs = categoryRestConsumer.findAllCategoryDTOs();

        assertNotNull(categoryDTOs);
        assertEquals(ARRAY_LIST_OF_CATEGORY_DTOS, categoryDTOs);

        Mockito.verify(restTemplate, Mockito.times(ONCE)).getForEntity(url + "/info", List.class);
    }

    @Test
    void shouldFindAllSubCategories() {

        Mockito.when(restTemplate.getForEntity(url + "/subs", List.class))
                .thenReturn(new ResponseEntity<>(ARRAY_LIST_OF_CATEGORY, HttpStatus.OK));

        List<Category> categories = categoryRestConsumer.findAllSubCategories();

        assertNotNull(categories);
        assertEquals(ARRAY_LIST_OF_CATEGORY, categories);

        Mockito.verify(restTemplate, Mockito.times(ONCE)).getForEntity(url + "/subs", List.class);
    }

    @Test
    void shouldFindCategoryDTOById() {

        Mockito.when(restTemplate.getForEntity(url + "/info/" + FIRST_CATEGORY_ID, CategoryDTO.class))
                .thenReturn(new ResponseEntity<>(FIRST_CATEGORY_DTO, HttpStatus.OK));

        CategoryDTO categoryDTO = categoryRestConsumer.findCategoryDTOById(FIRST_CATEGORY_ID);

        assertNotNull(categoryDTO);
        assertEquals(FIRST_CATEGORY_DTO, categoryDTO);

        Mockito.verify(restTemplate, Mockito.times(ONCE))
                .getForEntity(url + "/info/" + FIRST_CATEGORY_ID, CategoryDTO.class);
    }

    @Test
    void shouldFindSubCategoryDTOsByCategoryId() {

        Mockito.when(restTemplate.getForEntity(url + "/info/" + FIRST_CATEGORY_ID + "/subs", List.class))
                .thenReturn(new ResponseEntity<>(ARRAY_LIST_OF_CATEGORY_DTOS, HttpStatus.OK));

        List<CategoryDTO> categoryDTOs = categoryRestConsumer.findSubCategoryDTOsByCategoryId(FIRST_CATEGORY_ID);

        assertNotNull(categoryDTOs);
        assertEquals(ARRAY_LIST_OF_CATEGORY_DTOS, categoryDTOs);

        Mockito.verify(restTemplate, Mockito.times(ONCE))
                .getForEntity(url + "/info/" + FIRST_CATEGORY_ID + "/subs", List.class);
    }

    @Test
    void shouldAddCategory() {

        Mockito.when(restTemplate.postForEntity(url, FIRST_CATEGORY, Category.class))
                .thenReturn(new ResponseEntity<>(FIRST_CATEGORY, HttpStatus.OK));

        Category category = categoryRestConsumer.add(FIRST_CATEGORY);

        assertNotNull(category);
        assertEquals(FIRST_CATEGORY, category);

        Mockito.verify(restTemplate, Mockito.times(ONCE))
                .postForEntity(url, FIRST_CATEGORY, Category.class);
    }

    @Test
    void shouldUpdateCategory() {

        categoryRestConsumer.update(FIRST_CATEGORY);
        Mockito.verify(restTemplate, Mockito.times(ONCE)).put(url + "/" + FIRST_CATEGORY_ID, FIRST_CATEGORY);
    }

    @Test
    void shouldDeleteCategory() {

        categoryRestConsumer.delete(FIRST_CATEGORY_ID);
        Mockito.verify(restTemplate, Mockito.times(ONCE)).delete(url + "/" + FIRST_CATEGORY_ID);
    }

    @Test
    void shouldFindAllPossibleParentsForId() {

        Mockito.when(restTemplate.getForEntity(url + "/possibleparents/" + FIRST_CATEGORY_ID, List.class))
                .thenReturn(new ResponseEntity<>(ARRAY_LIST_OF_CATEGORY, HttpStatus.OK));

        List<Category> categories = categoryRestConsumer.findAllPossibleParentsForId(FIRST_CATEGORY_ID);

        assertNotNull (categories);
        assertEquals(ARRAY_LIST_OF_CATEGORY, categories);

        Mockito.verify(restTemplate, Mockito.times(ONCE))
                .getForEntity(url + "/possibleparents/" + FIRST_CATEGORY_ID, List.class);
    }

    @Test
    void shouldFindAllPossibleParents() {

        Mockito.when(restTemplate.getForEntity(url + "/possibleparents", List.class))
                .thenReturn(new ResponseEntity<>(ARRAY_LIST_OF_CATEGORY, HttpStatus.OK));

        List<Category> categories = categoryRestConsumer.findAllPossibleParents();

        assertNotNull(categories);
        assertEquals(ARRAY_LIST_OF_CATEGORY, categories);

        Mockito.verify(restTemplate, Mockito.times(ONCE))
                .getForEntity(url + "/possibleparents", List.class);
    }

    @AfterEach
    void afterEach() {

        Mockito.verifyNoMoreInteractions(restTemplate);
    }

    private static Category createCategory(Integer id) {

        Category category = new Category();
        category.setCategoryId(id);
        category.setCategoryName(CATEGORY_NAME + id);
        category.setParentId(CATEGORY_PARENT_ID);

        return category;
    }

    private static CategoryDTO createCategoryDTO(Integer id) {

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(id);
        categoryDTO.setCategoryName(CATEGORY_NAME + id);
        categoryDTO.setProductsAmount(CATEGORY_PRODUCT_AMOUNT);

        return categoryDTO;
    }
}