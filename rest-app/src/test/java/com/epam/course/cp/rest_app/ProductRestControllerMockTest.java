package com.epam.course.cp.rest_app;

import com.epam.course.cp.dto.ProductDTO;
import com.epam.course.cp.model.Product;
import com.epam.course.cp.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
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

import java.time.LocalDate;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class ProductRestControllerMockTest {

    private static ObjectMapper MAPPER;

    private static final Integer ONCE = 1;

    private static final Integer FIRST_PRODUCT_ID = 1;
    private static final Integer SECOND_PRODUCT_ID = 2;

    private static final String TEST_PRODUCT_NAME = "TestProduct";
    private static final Integer TEST_PRODUCT_AMOUNT = 2500;
    private static final LocalDate TEST_PRODUCT_DATE_ADDED = LocalDate.of(2019, 1, 1);
    private static final Integer TEST_PRODUCT_CATEGORY_ID = 1;
    private static final String TEST_PRODUCT_CATEGORY_NAME = "TestCategory";
    private static final String TEST_PRODUCT_SUBCATEGORY_NAME = "TestSybCategory";

    private static Product FIRST_PRODUCT;
    private static Product SECOND_PRODUCT;

    private static ArrayList<Product> ARRAY_LIST_OF_PRODUCTS;

    private static ProductDTO FIRST_PRODUCT_DTO;
    private static ProductDTO SECOND_PRODUCT_DTO;

    private static ArrayList<ProductDTO> ARRAY_LIST_OF_PRODUCTS_DTO;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductRestController controller;

    private MockMvc mockMvc;

    @BeforeAll
    static void init() {

        MAPPER = new ObjectMapper();
        MAPPER.registerModule(new JavaTimeModule());

        FIRST_PRODUCT = createProduct(FIRST_PRODUCT_ID);
        SECOND_PRODUCT = createProduct(SECOND_PRODUCT_ID);

        ARRAY_LIST_OF_PRODUCTS = new ArrayList<>();
        ARRAY_LIST_OF_PRODUCTS.add(FIRST_PRODUCT);
        ARRAY_LIST_OF_PRODUCTS.add(SECOND_PRODUCT);

        FIRST_PRODUCT_DTO = createProductDTO(FIRST_PRODUCT_ID);
        SECOND_PRODUCT_DTO = createProductDTO(SECOND_PRODUCT_ID);

        ARRAY_LIST_OF_PRODUCTS_DTO = new ArrayList<>();
        ARRAY_LIST_OF_PRODUCTS_DTO.add(FIRST_PRODUCT_DTO);
        ARRAY_LIST_OF_PRODUCTS_DTO.add(SECOND_PRODUCT_DTO);
    }

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    void shouldFindAllProducts() throws Exception {

        Mockito.when(productService.findAll()).thenReturn(ARRAY_LIST_OF_PRODUCTS);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/products")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(MAPPER.writeValueAsString(ARRAY_LIST_OF_PRODUCTS)))
        ;

        Mockito.verify(productService, Mockito.times(ONCE)).findAll();
    }

    @Test
    void shouldFindProductById() throws Exception {

        Mockito.when(productService.findById(anyInt())).thenReturn(FIRST_PRODUCT);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/1")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(MAPPER.writeValueAsString(FIRST_PRODUCT)))
        ;

        Mockito.verify(productService, Mockito.times(ONCE)).findById(anyInt());
    }

    @Test
    void shouldFindAllProductDTOs() throws Exception {

        Mockito.when(productService.findAllProductDTOs()).thenReturn(ARRAY_LIST_OF_PRODUCTS_DTO);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/info")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(MAPPER.writeValueAsString(ARRAY_LIST_OF_PRODUCTS_DTO)))
        ;

        Mockito.verify(productService, Mockito.times(ONCE)).findAllProductDTOs();
    }

    @Test
    void shouldFindProductDTOsByMixedFilter() throws Exception {

        Mockito.when(productService.findProductDTOsByFilter(any())).thenReturn(ARRAY_LIST_OF_PRODUCTS_DTO);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/products/filter")
                        .param("from", "2012-01-01")
                        .param("to", "2019-01-01")
                        .param("id", "1")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        ).andExpect(MockMvcResultMatchers.jsonPath("$[0].categoryName",
                Matchers.is(TEST_PRODUCT_CATEGORY_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].subCategoryName",
                        Matchers.is(TEST_PRODUCT_SUBCATEGORY_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].categoryName",
                        Matchers.is(TEST_PRODUCT_CATEGORY_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].subCategoryName",
                        Matchers.is(TEST_PRODUCT_SUBCATEGORY_NAME)))
        ;

        Mockito.verify(productService, Mockito.times(ONCE)).findProductDTOsByFilter(any());
    }

    @Test
    void shouldAddProduct() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(MAPPER.writeValueAsString(FIRST_PRODUCT))
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(productService, Mockito.times(ONCE)).add(any());
    }

    @Test
    void shouldUpdateProduct() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(MAPPER.writeValueAsString(FIRST_PRODUCT))
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(productService, Mockito.times(ONCE)).update(any());
    }

    @Test
    void shouldDeleteProduct() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/products/1")
        ).andExpect(MockMvcResultMatchers.status().isOk())
        ;

        Mockito.verify(productService, Mockito.times(ONCE)).delete(any());
    }

    @AfterEach
    void afterEach() {

        Mockito.verifyNoMoreInteractions(productService);
        Mockito.reset(productService);
    }

    private static Product createProduct(Integer id) {

        Product product = new Product();
        product.setProductId(id);
        product.setProductName(TEST_PRODUCT_NAME + id);
        product.setProductAmount(TEST_PRODUCT_AMOUNT);
        product.setDateAdded(TEST_PRODUCT_DATE_ADDED);
        product.setCategoryId(TEST_PRODUCT_CATEGORY_ID);

        return product;
    }

    private static ProductDTO createProductDTO(Integer id) {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(id);
        productDTO.setProductName(TEST_PRODUCT_NAME + id);
        productDTO.setProductAmount(TEST_PRODUCT_AMOUNT + id);
        productDTO.setDateAdded(TEST_PRODUCT_DATE_ADDED);
        productDTO.setCategoryId(TEST_PRODUCT_CATEGORY_ID);
        productDTO.setCategoryName(TEST_PRODUCT_CATEGORY_NAME);
        productDTO.setSubCategoryName(TEST_PRODUCT_SUBCATEGORY_NAME);

        return productDTO;
    }
}