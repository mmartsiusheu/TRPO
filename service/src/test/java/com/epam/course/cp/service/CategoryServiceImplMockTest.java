package com.epam.course.cp.service;

import com.epam.course.cp.dao.CategoryDao;
import com.epam.course.cp.dto.CategoryDTO;
import com.epam.course.cp.model.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplMockTest {

    private static final Integer ONCE = 1;

    private static final String CATEGORY_NAME = "TestCategory";
    private static final Integer CATEGORY_PARENT_ID = 1;

    private static final Integer FIRST_CATEGORY_ID = 1;
    private static final Integer SECOND_CATEGORY_ID = 2;
    private static final Integer WRONG_CATEGORY_ID = Integer.MAX_VALUE;

    private static final Integer CATEGORY_DTOS_AMOUNT = 2;
    private static final Integer CATEGORY_AMOUNT = 2;

    private static final String CATEGORY_DTO_NAME = "TestCategoryDTO";
    private static final Integer CATEGORY_DTO_PRODUCT_AMOUNT = 2500;

    private static Category FIRST_CATEGORY;
    private static Category SECOND_CATEGORY;
    private static Category PARENT_CATEGORY;

    private static CategoryDTO FIRST_CATEGORY_DTO;
    private static CategoryDTO SECOND_CATEGORY_DTO;

    @Mock
    private CategoryDao categoryDao;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeAll
    static void init() {

        FIRST_CATEGORY = createCategory(FIRST_CATEGORY_ID);
        SECOND_CATEGORY = createCategory(SECOND_CATEGORY_ID);

        FIRST_CATEGORY_DTO = createCategoryDTO(FIRST_CATEGORY_ID);
        SECOND_CATEGORY_DTO = createCategoryDTO(SECOND_CATEGORY_ID);

        PARENT_CATEGORY = createCategory(FIRST_CATEGORY_ID);
        PARENT_CATEGORY.setParentId(0);
    }

    @Test
    void shouldFindCategoryById() {

        Mockito.when(categoryDao.findById(any())).thenReturn(Optional.of(FIRST_CATEGORY));

        Category category = categoryService.findById(FIRST_CATEGORY_ID);
        assertNotNull(category);
        assertEquals(FIRST_CATEGORY_ID, category.getCategoryId());

        Mockito.verify(categoryDao, Mockito.times(ONCE)).findById(anyInt());
    }

    @Test
    void shouldGetExceptionByWrongId() {

        Mockito.when(categoryDao.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            categoryService.findById(WRONG_CATEGORY_ID);
        });

        Mockito.verify(categoryDao, Mockito.times(ONCE)).findById(anyInt());
    }

    @Test
    void shouldFindAllCategoryDTOs() {

        Mockito.when(categoryDao.findAllCategoryDTOs())
                .thenReturn(Stream.of(FIRST_CATEGORY_DTO, SECOND_CATEGORY_DTO));

        List<CategoryDTO> categoryDTOs = categoryService.findAllCategoryDTOs();
        assertNotNull(categoryDTOs);
        assertTrue(CATEGORY_DTOS_AMOUNT == categoryDTOs.size());

        Mockito.verify(categoryDao, Mockito.times(ONCE)).findAllCategoryDTOs();
    }

    @Test
    void shouldFindCategoryDTOsByParentId() {

        Mockito.when(categoryDao.findSubCategoryDTOsByCategoryId(anyInt()))
                .thenReturn(Stream.of(FIRST_CATEGORY_DTO, SECOND_CATEGORY_DTO));

        List<CategoryDTO> subCategoryDTOs = categoryService.findSubCategoryDTOsByCategoryId(CATEGORY_PARENT_ID);
        assertNotNull(subCategoryDTOs);
        assertTrue(CATEGORY_DTOS_AMOUNT == subCategoryDTOs.size());

        Mockito.verify(categoryDao, Mockito.times(ONCE)).findSubCategoryDTOsByCategoryId(anyInt());
    }

    @Test
    void shouldFindCategoryDTOById() {

        Mockito.when(categoryDao.findCategoryDTOById(FIRST_CATEGORY_ID))
                .thenReturn(Optional.of(FIRST_CATEGORY_DTO));

        CategoryDTO categoryDTO = categoryService.findCategoryDTOById(FIRST_CATEGORY_ID);

        assertNotNull(categoryDTO);
        assertEquals(FIRST_CATEGORY_ID, categoryDTO.getCategoryId());

        Mockito.verify(categoryDao, Mockito.times(ONCE)).findCategoryDTOById(FIRST_CATEGORY_ID);
    }

    @Test
    void shouldFindAllSubCategories() {

        Mockito.when(categoryDao.findAllSubCategories()).thenReturn(Stream.of(FIRST_CATEGORY, SECOND_CATEGORY));

        List<Category> categoryDTOs = categoryService.findAllSubCategories();
        assertNotNull(categoryDTOs);
        assertTrue(CATEGORY_AMOUNT == categoryDTOs.size());

        Mockito.verify(categoryDao, Mockito.times(ONCE)).findAllSubCategories();
    }

    @Test
    void shouldAddCategory() {

        Mockito.when(categoryDao.add(any())).thenReturn(Optional.of(FIRST_CATEGORY));
        categoryService.add(any());
        Mockito.verify(categoryDao, Mockito.times(ONCE)).add(any());
    }

    @Test
    void shouldUpdateCategory() {

        categoryService.update(any(Category.class));
        Mockito.verify(categoryDao, Mockito.times(ONCE)).update(any());
    }

    @Test
    void shouldDeleteCategory() {

        categoryService.delete(anyInt());
        Mockito.verify(categoryDao, Mockito.times(ONCE)).delete(anyInt());
    }

    @Test
    void shouldFindAllPossibleParents() {

        Mockito.when(categoryDao.findAllPossibleParents()).thenReturn(Stream.of(FIRST_CATEGORY, SECOND_CATEGORY));

        List<Category> categories = categoryService.findAllPossibleParents();

        assertNotNull(categories);
        assertTrue(CATEGORY_AMOUNT == categories.size());

        Mockito.verify(categoryDao, Mockito.times(ONCE)).findAllPossibleParents();
    }

    @Test
    void shouldFindAllPossibleParentsForIdWithEmptyResult() {

        Mockito.when(categoryDao.findById(any())).thenReturn(Optional.of(PARENT_CATEGORY));
        Mockito.when(categoryDao.findSubCategoryDTOsByCategoryId(any())).thenReturn(Stream.of(SECOND_CATEGORY_DTO));

        List<Category> categories = categoryService.findAllPossibleParentsForId(1);

        assertNotNull(categories);
        assertTrue(categories.isEmpty());

        Mockito.verify(categoryDao, Mockito.times(ONCE)).findById(any());
        Mockito.verify(categoryDao, Mockito.times(ONCE)).findSubCategoryDTOsByCategoryId(any());
    }

    @AfterEach
    void afterEach() {

        Mockito.verifyNoMoreInteractions(categoryDao);
        Mockito.reset(categoryDao);
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
        categoryDTO.setCategoryName(CATEGORY_DTO_NAME + id);
        categoryDTO.setProductsAmount(CATEGORY_DTO_PRODUCT_AMOUNT);

        return categoryDTO;
    }

}