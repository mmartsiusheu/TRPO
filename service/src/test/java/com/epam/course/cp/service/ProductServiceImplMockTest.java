package com.epam.course.cp.service;

import com.epam.course.cp.dao.ProductDao;
import com.epam.course.cp.dto.Filter;
import com.epam.course.cp.dto.ProductDTO;
import com.epam.course.cp.model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplMockTest {

    private static final Integer ONCE = 1;

    private static final String TEST_PRODUCT_NAME = "TestProduct";
    private static final Integer TEST_PRODUCT_AMOUNT = 2500;
    private static final LocalDate TEST_DATE_ADDED = LocalDate.of(2018, 1, 1);
    private static final Integer TEST_CATEGORY_ID = 5;
    private static final String TEST_CATEGORY_NAME = "TestCategory";
    private static final String TEST_SUBCATEGORY_NAME = "TestSubCategory";

    private static final Integer PRODUCTS_AMOUNT = 2;

    private static final Integer FIRST_PRODUCT_ID = 1;
    private static final Integer SECOND_PRODUCT_ID = 2;

    private static Product FIRST_PRODUCT;
    private static Product SECOND_PRODUCT;

    private static ProductDTO FIRST_PRODUCT_DTO;
    private static ProductDTO SECOND_PRODUCT_DTO;

    private final static LocalDate FILTER_DATE_BEGIN = LocalDate.of(2015, 1, 1);
    private final static LocalDate FILTER_DATE_END = LocalDate.of(2019, 1, 1);

    private static Filter FILTER_WITH_DATES;
    private static Filter FILTER_WITH_DATES_ADN_ID;

    @Mock
    private ProductDao productDao;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeAll
    static void init() {

        FIRST_PRODUCT = createProduct(FIRST_PRODUCT_ID);
        SECOND_PRODUCT = createProduct(SECOND_PRODUCT_ID);

        FIRST_PRODUCT_DTO = createProductDTO(FIRST_PRODUCT_ID);
        SECOND_PRODUCT_DTO = createProductDTO(SECOND_PRODUCT_ID);

        FILTER_WITH_DATES = new Filter();
        FILTER_WITH_DATES.setDateBegin(FILTER_DATE_BEGIN);
        FILTER_WITH_DATES.setDateEnd(FILTER_DATE_END);

        FILTER_WITH_DATES_ADN_ID = new Filter();
        FILTER_WITH_DATES_ADN_ID.setDateBegin(FILTER_DATE_BEGIN);
        FILTER_WITH_DATES_ADN_ID.setDateEnd(FILTER_DATE_END);
        FILTER_WITH_DATES_ADN_ID.setCategoryId(1);
    }

    @Test
    void shouldFindAllProducts() {

        Mockito.when(productDao.findAll())
                .thenReturn(Stream.of(FIRST_PRODUCT, SECOND_PRODUCT));

        List<Product> products = productService.findAll();

        assertNotNull(products);
        assertTrue(PRODUCTS_AMOUNT == products.size());

        Mockito.verify(productDao, Mockito.times(ONCE)).findAll();
        Mockito.verifyNoMoreInteractions(productDao);
    }

    @Test
    void shouldFindProductById() {

        Mockito.when(productDao.findById(any())).thenReturn(Optional.of(FIRST_PRODUCT));

        Product product = productService.findById(FIRST_PRODUCT_ID);

        assertNotNull(product);
        assertEquals(FIRST_PRODUCT_ID, product.getProductId());

        Mockito.verify(productDao, Mockito.times(ONCE)).findById(any());
        Mockito.verifyNoMoreInteractions(productDao);
    }

    @Test
    void findAllProductDTOs() {

        Mockito.when(productDao.findAllProductDTOs())
                .thenReturn(Stream.of(FIRST_PRODUCT_DTO, SECOND_PRODUCT_DTO));

        List<ProductDTO> productDTOs = productService.findAllProductDTOs();

        assertNotNull(productDTOs);
        assertTrue(PRODUCTS_AMOUNT == productDTOs.size());

        Mockito.verify(productDao, Mockito.times(ONCE)).findAllProductDTOs();
        Mockito.verifyNoMoreInteractions(productDao);
    }

    @Test
    void findProductDTOsByCategoryId() {

        Mockito.when(productDao.findProductDTOsByCategoryId(anyInt()))
                .thenReturn(Stream.of(FIRST_PRODUCT_DTO, SECOND_PRODUCT_DTO));

        List<ProductDTO> productDTOs = productService.findProductDTOsByCategoryId(TEST_CATEGORY_ID);

        assertNotNull(productDTOs);
        assertTrue(PRODUCTS_AMOUNT == productDTOs.size());

        Mockito.verify(productDao, Mockito.times(ONCE)).findProductDTOsByCategoryId(anyInt());
        Mockito.verifyNoMoreInteractions(productDao);
    }

    @Test
    void findProductDTOsFromDateInterval() {

        Mockito.when(productDao.findProductDTOsFromDateInterval(any(), any()))
                .thenReturn(Stream.of(FIRST_PRODUCT_DTO, SECOND_PRODUCT_DTO));

        List<ProductDTO> productDTOs = productService.findProductDTOsByFilter(FILTER_WITH_DATES);

        assertNotNull(productDTOs);
        assertTrue(PRODUCTS_AMOUNT == productDTOs.size());

        Mockito.verify(productDao, Mockito.times(ONCE)).findProductDTOsFromDateInterval(any(), any());
        Mockito.verifyNoMoreInteractions(productDao);
    }

    @Test
    void findProductDTOsByMixedFilter() {

        Mockito.when(productDao.findProductDTOsByMixedFilter(any(), any(), any()))
                .thenReturn(Stream.of(FIRST_PRODUCT_DTO, SECOND_PRODUCT_DTO));

        List<ProductDTO> productDTOs = productService.findProductDTOsByFilter(FILTER_WITH_DATES_ADN_ID);

        assertNotNull(productDTOs);
        assertTrue(PRODUCTS_AMOUNT == productDTOs.size());

        Mockito.verify(productDao, Mockito.times(ONCE)).findProductDTOsByMixedFilter(any(), any(), any());
        Mockito.verifyNoMoreInteractions(productDao);
    }

    @Test
    void add() {

        Mockito.when(productDao.add(any())).thenReturn(Optional.of(FIRST_PRODUCT));
        productService.add(new Product());
        Mockito.verify(productDao, Mockito.times(ONCE)).add(any());
        Mockito.verifyNoMoreInteractions(productDao);
    }

    @Test
    void update() {

        productService.update(any());
        Mockito.verify(productDao, Mockito.times(ONCE)).update(any());
        Mockito.verifyNoMoreInteractions(productDao);
    }

    @Test
    void delete() {

        productService.delete(any());
        Mockito.verify(productDao, Mockito.times(ONCE)).delete(any());
        Mockito.verifyNoMoreInteractions(productDao);
    }

    private static Product createProduct(Integer id) {

        Product product = new Product();
        product.setProductId(id);
        product.setProductName(TEST_PRODUCT_NAME + id);
        product.setProductAmount(TEST_PRODUCT_AMOUNT);
        product.setDateAdded(TEST_DATE_ADDED);
        product.setCategoryId(TEST_CATEGORY_ID);

        return product;
    }

    private static ProductDTO createProductDTO(Integer id) {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(id);
        productDTO.setProductName(TEST_PRODUCT_NAME + id);
        productDTO.setProductAmount(TEST_PRODUCT_AMOUNT);
        productDTO.setDateAdded(TEST_DATE_ADDED);
        productDTO.setCategoryId(TEST_CATEGORY_ID);
        productDTO.setCategoryName(TEST_CATEGORY_NAME);
        productDTO.setSubCategoryName(TEST_SUBCATEGORY_NAME);

        return productDTO;
    }
}