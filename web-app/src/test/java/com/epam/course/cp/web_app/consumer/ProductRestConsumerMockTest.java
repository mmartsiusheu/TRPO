package com.epam.course.cp.web_app.consumer;

import com.epam.course.cp.dto.CategoryDTO;
import com.epam.course.cp.dto.Filter;
import com.epam.course.cp.dto.ProductDTO;
import com.epam.course.cp.model.Category;
import com.epam.course.cp.model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class ProductRestConsumerMockTest {

    private static final Integer ONCE = 1;

    private static final Integer FIRST_PRODUCT_ID = 1;

    private static final String PRODUCT_NAME = "TestProduct";
    private static final Integer PRODUCT_CATEGORY_ID = 1;
    private static final Integer PRODUCT_AMOUNT = 2500;
    private static final LocalDate PRODUCT_DATE_ADDED = LocalDate.of(2018, 1, 1);
    private static final String PRODUCT_CATEGORY_NAME = "TestCategory";
    private static final String PRODUCT_SUBCATEGORY_NAME = "TestSubCategory";

    private static Product FIRST_PRODUCT;

    private static ProductDTO FIRST_PRODUCT_DTO;

    private static Filter TEST_FILTER;

    private static List<Product> ARRAY_LIST_OF_PRODUCTS;

    private static List<ProductDTO> ARRAY_LIST_OF_PRODUCT_DTOS;

    private RestTemplate restTemplate;

    private String url = "/rest/products";

    private ProductRestConsumer productRestConsumer;


    @BeforeAll
    static void init() {

        FIRST_PRODUCT = createProduct(FIRST_PRODUCT_ID);

        FIRST_PRODUCT_DTO = createProductDTO(FIRST_PRODUCT_ID);

        ARRAY_LIST_OF_PRODUCT_DTOS = new ArrayList<>();
        ARRAY_LIST_OF_PRODUCT_DTOS.add(createProductDTO(2));
        ARRAY_LIST_OF_PRODUCT_DTOS.add(createProductDTO(3));

        ARRAY_LIST_OF_PRODUCTS = new ArrayList<>();
        ARRAY_LIST_OF_PRODUCTS.add(createProduct(2));
        ARRAY_LIST_OF_PRODUCTS.add(createProduct(3));

        TEST_FILTER = new Filter();
        TEST_FILTER.setCategoryId(FIRST_PRODUCT_ID);
        TEST_FILTER.setDateBegin(LocalDate.now().withDayOfMonth(1));
        TEST_FILTER.setDateEnd(LocalDate.now());
    }

    @BeforeEach
    void setUp() {

        restTemplate = Mockito.mock(RestTemplate.class);
        productRestConsumer = new ProductRestConsumer(url, restTemplate);
    }

    @Test
    void shouldFindAll() {

        Mockito.when(restTemplate.getForEntity(url, List.class))
                .thenReturn(new ResponseEntity<>(ARRAY_LIST_OF_PRODUCTS, HttpStatus.OK));

        List<Product> products = productRestConsumer.findAll();

        assertNotNull(products);
        assertEquals(ARRAY_LIST_OF_PRODUCTS, products);

        Mockito.verify(restTemplate, Mockito.times(ONCE)).getForEntity(url, List.class);
    }

    @Test
    void shouldFindById() {

        Mockito.when(restTemplate.getForEntity(url + "/" + FIRST_PRODUCT_ID, Product.class))
                .thenReturn(new ResponseEntity<>(FIRST_PRODUCT, HttpStatus.OK));

        Product product = productRestConsumer.findById(FIRST_PRODUCT_ID);

        assertNotNull(product);
        assertEquals(FIRST_PRODUCT, product);

        Mockito.verify(restTemplate, Mockito.times(ONCE)).getForEntity(url + "/" + FIRST_PRODUCT_ID, Product.class);
    }

    @Test
    void shouldFindAllProductDTOs() {

        Mockito.when(restTemplate.getForEntity(url + "/info", List.class))
                .thenReturn(new ResponseEntity<>(ARRAY_LIST_OF_PRODUCT_DTOS, HttpStatus.OK));

        List<ProductDTO> productDTOs = productRestConsumer.findAllProductDTOs();

        assertNotNull(productDTOs);
        assertEquals(ARRAY_LIST_OF_PRODUCT_DTOS, productDTOs);

        Mockito.verify(restTemplate, Mockito.times(ONCE)).getForEntity(url + "/info", List.class);
    }

    @Test
    void shouldFindProductDTOsByCategoryId() {

        Mockito.when(restTemplate.getForEntity(url + "/filter?id=" + FIRST_PRODUCT_ID, List.class))
                .thenReturn(new ResponseEntity<>(ARRAY_LIST_OF_PRODUCT_DTOS, HttpStatus.OK));

        List<ProductDTO> productDTOs = productRestConsumer.findProductDTOsByCategoryId(FIRST_PRODUCT_ID);

        assertNotNull(productDTOs);
        assertEquals(ARRAY_LIST_OF_PRODUCT_DTOS, productDTOs);

        Mockito.verify(restTemplate, Mockito.times(ONCE))
                .getForEntity(url + "/filter?id=" + FIRST_PRODUCT_ID, List.class);
    }

    @Test
    void shouldFindProductDTOsByFilter() {

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromUriString(url + "/filter")
                .queryParam("id", FIRST_PRODUCT_ID)
                .queryParam("from", LocalDate.now().withDayOfMonth(1))
                .queryParam("to", LocalDate.now());

        Mockito.when(restTemplate.getForEntity(builder.toUriString(), List.class))
                .thenReturn(new ResponseEntity<>(ARRAY_LIST_OF_PRODUCT_DTOS, HttpStatus.OK));

        List<ProductDTO> productDTOs = productRestConsumer.findProductDTOsByFilter(TEST_FILTER);

        assertNotNull(productDTOs);
        assertEquals(ARRAY_LIST_OF_PRODUCT_DTOS, productDTOs);

        Mockito.verify(restTemplate, Mockito.times(ONCE)).getForEntity(builder.toUriString(), List.class);
    }

    @Test
    void shouldAddProduct() {

        Mockito.when(restTemplate.postForEntity(url, FIRST_PRODUCT, Product.class))
                .thenReturn(new ResponseEntity<>(FIRST_PRODUCT, HttpStatus.OK));

        Product product = productRestConsumer.add(FIRST_PRODUCT);

        assertNotNull(product);
        assertEquals(FIRST_PRODUCT, product);

        Mockito.verify(restTemplate, Mockito.times(ONCE)).postForEntity(url, FIRST_PRODUCT, Product.class);
    }

    @Test
    void shouldUpdateProduct() {

        productRestConsumer.update(FIRST_PRODUCT);
        Mockito.verify(restTemplate, Mockito.times(ONCE)).put(url +"/" + FIRST_PRODUCT_ID, FIRST_PRODUCT);
    }

    @Test
    void shouldDeleteProduct() {

        productRestConsumer.delete(FIRST_PRODUCT_ID);
        Mockito.verify(restTemplate, Mockito.times(ONCE)).delete(url +"/" + FIRST_PRODUCT_ID);
    }

    private static Product createProduct(Integer id) {

        Product product = new Product();
        product.setProductId(id);
        product.setProductName(PRODUCT_NAME + id);
        product.setProductAmount(PRODUCT_AMOUNT);
        product.setDateAdded(PRODUCT_DATE_ADDED);
        product.setCategoryId(PRODUCT_CATEGORY_ID);

        return product;
    }

    private static ProductDTO createProductDTO(Integer id) {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(id);
        productDTO.setProductName(PRODUCT_NAME + id);
        productDTO.setProductAmount(PRODUCT_AMOUNT);
        productDTO.setDateAdded(PRODUCT_DATE_ADDED);
        productDTO.setCategoryId(PRODUCT_CATEGORY_ID);
        productDTO.setCategoryName(PRODUCT_CATEGORY_NAME);
        productDTO.setSubCategoryName(PRODUCT_SUBCATEGORY_NAME);

        return productDTO;
    }
}