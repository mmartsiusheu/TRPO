package com.epam.course.cp.web_app;

import com.epam.course.cp.dto.ProductDTO;
import com.epam.course.cp.model.Category;
import com.epam.course.cp.model.Product;
import com.epam.course.cp.service.CategoryService;
import com.epam.course.cp.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:web-test.xml")
class ProductControllerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Integer ONCE = 1;

    private static final String PRODUCT_NAME = "TestProduct";
    private static final int PRODUCT_AMOUNT = 2500;
    private static final String CATEGORY_NAME = "TestCategory";
    private static final String SUBCATEGORY_NAME = "TestSubCategory";
    private static final Integer CATEGORY_PARENT_ID = 1;

    private static Product PRODUCT_FOR_UPDATE;

    private static ArrayList<Category> ARRAY_LIST_OF_CATEGORIES;

    private static ArrayList<ProductDTO> ARRAY_LIST_OF_PRODUCT_DTOS;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @BeforeAll
    static void init() {

        PRODUCT_FOR_UPDATE = new Product();
        PRODUCT_FOR_UPDATE.setProductId(1);
        PRODUCT_FOR_UPDATE.setProductName("TestProduct");
        PRODUCT_FOR_UPDATE.setProductAmount(2500);
        PRODUCT_FOR_UPDATE.setCategoryId(5);

        ARRAY_LIST_OF_CATEGORIES = new ArrayList<>();
        ARRAY_LIST_OF_CATEGORIES.add(createCategory(2));
        ARRAY_LIST_OF_CATEGORIES.add(createCategory(3));

        ARRAY_LIST_OF_PRODUCT_DTOS = new ArrayList<>();
        ARRAY_LIST_OF_PRODUCT_DTOS.add(createProductDTO(1));
        ARRAY_LIST_OF_PRODUCT_DTOS.add(createProductDTO(2));

    }

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void shouldGoToUpdateProduct() throws Exception {

        Mockito.when(productService.findById(anyInt())).thenReturn(PRODUCT_FOR_UPDATE);
        Mockito.when(categoryService.findAllSubCategories()).thenReturn(ARRAY_LIST_OF_CATEGORIES);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/product/1")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("product"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<p class=\"h2 text-center\">Product Details</p>")))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<span class=\"text-uppercase mr-auto\">Edit Product</span>")))
        ;

        Mockito.verify(productService, Mockito.times(ONCE)).findById(anyInt());
        Mockito.verify(categoryService, Mockito.times(ONCE)).findAllSubCategories();
    }

    @Test
    void shouldUpdateProduct() throws Exception {

        Mockito.when(categoryService.findAllSubCategories()).thenReturn(ARRAY_LIST_OF_CATEGORIES);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/product/1")
                        .param("productId", "1")
                        .param("productName", "TestProduct")
                        .param("productAmount", "500")
                        .param("categoryId", "5")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/products"))
        ;

        Mockito.verify(productService, Mockito.times(ONCE)).update(any());
        Mockito.verify(categoryService, Mockito.times(ONCE)).findAllSubCategories();
    }

    @Test
    void shouldGoToAddProduct() throws Exception {

        Mockito.when(categoryService.findAllSubCategories()).thenReturn(ARRAY_LIST_OF_CATEGORIES);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/product")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("product"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<p class=\"h2 text-center\">Product Details</p>")))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<span class=\"text-uppercase mr-auto\">Add New Product</span>")))
        ;

        Mockito.verify(categoryService, Mockito.times(ONCE)).findAllSubCategories();
    }

    @Test
    void shouldAddProduct() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/product")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("productId", "1")
                        .param("productName", "TestProduct")
                        .param("productAmount", "500")
                        .param("categoryId", "5")
        ).andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/products"))
        ;

        Mockito.verify(productService, Mockito.times(ONCE)).add(any());
    }

    @Test
    void findAllProductsDTOs() throws Exception {

        Mockito.when(categoryService.findAllPossibleParents()).thenReturn(ARRAY_LIST_OF_CATEGORIES);
        Mockito.when(productService.findProductDTOsByFilter(any())).thenReturn(ARRAY_LIST_OF_PRODUCT_DTOS);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("products"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<span class=\"text-uppercase\">List of All Products</span>")))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<th scope=\"row\">" + CATEGORY_NAME + "</th>")))
        ;

        Mockito.verify(productService, Mockito.times(ONCE)).findProductDTOsByFilter(any());
        Mockito.verify(categoryService, Mockito.times(ONCE)).findAllPossibleParents();
    }

    @Test
    void findAllProductsDTOsByFilter() throws Exception {

        Mockito.when(categoryService.findAllPossibleParents()).thenReturn(ARRAY_LIST_OF_CATEGORIES);
        Mockito.when(productService.findProductDTOsByFilter(any())).thenReturn(ARRAY_LIST_OF_PRODUCT_DTOS);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/products/filter")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.view().name("products"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<span class=\"text-uppercase\">List of All Products</span>")))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("<th scope=\"row\">" + CATEGORY_NAME + "</th>")))
        ;

        Mockito.verify(productService, Mockito.times(ONCE)).findProductDTOsByFilter(any());
        Mockito.verify(categoryService, Mockito.times(ONCE)).findAllPossibleParents();
    }

    @Test
    void deleteProduct() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/1")
        ).andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/products"))
        ;

        Mockito.verify(productService, Mockito.times(ONCE)).delete(any());
    }

    @AfterEach
    void afterEach() {

        Mockito.verifyNoMoreInteractions(categoryService, productService);
        Mockito.reset(categoryService, productService);
    }
    private static Category createCategory(Integer id) {

        Category category = new Category();
        category.setCategoryId(id);
        category.setCategoryName(CATEGORY_NAME + id);
        category.setParentId(CATEGORY_PARENT_ID);

        return category;
    }

    private static ProductDTO createProductDTO(Integer id) {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(id);
        productDTO.setProductName(PRODUCT_NAME);
        productDTO.setProductAmount(PRODUCT_AMOUNT);
        productDTO.setCategoryName(CATEGORY_NAME);
        productDTO.setSubCategoryName(SUBCATEGORY_NAME);
        productDTO.setDateAdded(LocalDate.now());

        return productDTO;
    }
}