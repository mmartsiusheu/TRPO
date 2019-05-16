package com.epam.course.cp.dao;

import com.epam.course.cp.dao.exception.DaoRuntimeException;
import com.epam.course.cp.dto.ProductDTO;
import com.epam.course.cp.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-db.xml", "classpath:dao-test.xml"})
@Transactional
@Rollback
class ProductDaoJdbcImplTest {

    private final static Integer PRODUCTS_AMOUNT = 8;

    private static final Integer TEST_PRODUCT_ID = 1;
    private static final String TEST_PRODUCT_NAME = "Red bricks";
    private static final Integer TEST_PRODUCT_AMOUNT = 2500;
    private static final LocalDate TEST_PRODUCT_DATE_ADDED = LocalDate.of(2012, 6, 18);
    private static final Integer TEST_PRODUCT_CATEGORY_ID = 5;

    private static final String NEW_PRODUCT_NAME = "Yellow bricks";
    private static final Integer NEW_PRODUCT_AMOUNT = 1750;
    private static final LocalDate NEW_PRODUCT_DATE_ADDED = LocalDate.of(2016, 7, 16);
    private static final Integer NEW_PRODUCT_CATEGORY_ID = 1;

    private static final Integer PARENT_CATEGORY_ID = 1;
    private static final Integer PARENT_CATEGORY_PRODUCTS_AMOUNT = 4;

    private static final Integer PRODUCT_ID_TO_DELETE = 1;

    private static final LocalDate DATE_INTERVAL_BEGIN = LocalDate.of(2018, 1, 1);
    private static final LocalDate DATE_INTERVAL_END = LocalDate.of(2019, 1, 1);
    private static final Integer PRODUCTS_AMOUNT_IN_DATE_INTERVAL = 4;

    private static final Integer PRODUCTS_AMOUNT_BY_MIXED_FILTER = 2;

    @Autowired
    private ProductDao productDao;

    @Test
    void shouldFindAllProducts() {

        Stream<Product> products = productDao.findAll();

        assertNotNull(products);
        assertTrue(PRODUCTS_AMOUNT == products.count());
    }

    @Test
    void shouldFindProductById() {

        Product product = productDao.findById(TEST_PRODUCT_ID).get();

        assertEquals(TEST_PRODUCT_NAME, product.getProductName());
        assertEquals(TEST_PRODUCT_AMOUNT, product.getProductAmount());
        assertEquals(TEST_PRODUCT_DATE_ADDED, product.getDateAdded());
        assertEquals(TEST_PRODUCT_CATEGORY_ID, product.getCategoryId());
    }

    @Test
    void shouldFindAllProductDTOs() {

        Stream<ProductDTO> productDTOs = productDao.findAllProductDTOs();

        assertNotNull(productDTOs);
        assertTrue(productDTOs.count() == PRODUCTS_AMOUNT);
    }

    @Test
    void shouldFindProductDTOsByCategoryId() {

        Stream<ProductDTO> productDTOs = productDao.findProductDTOsByCategoryId(PARENT_CATEGORY_ID);

        assertNotNull(productDTOs);
        assertTrue(PARENT_CATEGORY_PRODUCTS_AMOUNT == productDTOs.count());
    }

    @Test
    void shouldFindProductDTOsFromDateInterval() {

        Stream<ProductDTO> productDTOs = productDao.findProductDTOsFromDateInterval(DATE_INTERVAL_BEGIN, DATE_INTERVAL_END);

        assertNotNull(productDTOs);
        assertTrue(PRODUCTS_AMOUNT_IN_DATE_INTERVAL == productDTOs.count());

    }

    @Test
    void shouldFindProductDTOsByMixedFilter() {

        Stream<ProductDTO> productDTOs = productDao
                .findProductDTOsByMixedFilter(DATE_INTERVAL_BEGIN, DATE_INTERVAL_END, PARENT_CATEGORY_ID);

        assertNotNull(productDTOs);
        assertTrue(PRODUCTS_AMOUNT_BY_MIXED_FILTER == productDTOs.count());
    }

    @Test
    void shouldAddNewProduct() {

        Stream<Product> productsBeforeInsert = productDao.findAll();

        Product product = createProduct();

        Product newProduct = productDao.add(product).get();
        assertNotNull(newProduct.getProductId());

        Stream<Product> productsAfterInsert = productDao.findAll();
        assertTrue(1 == productsAfterInsert.count() - productsBeforeInsert.count());

    }

    @Test
    void shouldUpdateProduct() {

        Product product = createProduct();

        Product newProduct = productDao.add(product).get();
        assertNotNull(newProduct.getProductId());

        newProduct.setProductAmount(NEW_PRODUCT_AMOUNT + 10);
        newProduct.setProductName(NEW_PRODUCT_NAME + 10);

        productDao.update(newProduct);
        Product updateProduct = productDao.findById(newProduct.getProductId()).get();

        assertTrue(NEW_PRODUCT_AMOUNT + 10 == updateProduct.getProductAmount());
        assertEquals(NEW_PRODUCT_NAME + 10, updateProduct.getProductName());

    }

    @Test
    void updateNonExistentProduct() {

        Product product = createProduct();
        product.setProductId(Integer.MAX_VALUE);

        DaoRuntimeException exception =
                assertThrows(DaoRuntimeException.class, () -> {
                    productDao.update(product);
                });

        assertEquals("Failed to update product in DB", exception.getMessage());

    }

    @Test
    void shouldDeleteProduct() {

        productDao.delete(PRODUCT_ID_TO_DELETE);

        assertThrows(DataAccessException.class, () -> {
            productDao.findById(PRODUCT_ID_TO_DELETE);
        });
    }

    @Test
    void deleteNonExistentProduct() {

        DaoRuntimeException exception =
                assertThrows(DaoRuntimeException.class, () -> {
                    productDao.delete(Integer.MAX_VALUE);
                });

        assertEquals("Failed to delete product in DB", exception.getMessage());
    }

    private Product createProduct() {

        Product product = new Product();

        product.setProductName(NEW_PRODUCT_NAME);
        product.setProductAmount(NEW_PRODUCT_AMOUNT);
        product.setDateAdded(NEW_PRODUCT_DATE_ADDED);
        product.setCategoryId(NEW_PRODUCT_CATEGORY_ID);

        return product;
    }
}