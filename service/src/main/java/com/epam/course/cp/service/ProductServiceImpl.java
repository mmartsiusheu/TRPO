package com.epam.course.cp.service;

import com.epam.course.cp.dao.ProductDao;
import com.epam.course.cp.dto.Filter;
import com.epam.course.cp.dto.ProductDTO;
import com.epam.course.cp.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of {@code CategoryService} interface
 *
 * @see Product
 * @see ProductDTO
 * @see ProductDao
 */
@Service
public class ProductServiceImpl implements ProductService {

    /**
     * Default logger for that class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    /**
     * Data access layer object to execute task to data source
     */
    private final ProductDao productDao;

    /**
     * Constructs new object with given data access layer object
     *
     * @param productDao product data access layer object
     */
    @Autowired
    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    /**
     * Returns all {@code products} found
     *
     * @return {@code List} of a {@code products}
     */
    @Override
    public List<Product> findAll() {

        LOGGER.debug("findAll()");
        return productDao.findAll().collect(Collectors.toList());
    }

    /**
     * Returns a {@code product} with given id
     *
     * @param productId id of a {@code product} to find by
     * @return single {@code product}
     */
    @Override
    public Product findById(Integer productId) {

        LOGGER.debug("findById({})", productId);
        return productDao.findById(productId)
                .orElseThrow(() -> new RuntimeException("Failed to get product from DB"));
    }

    /**
     * Returns all {@code product Data Transfer Objects} found
     *
     * @return {@code List} of a {@code product Data Transfer Objects}s
     */
    @Override
    public List<ProductDTO> findAllProductDTOs() {

        LOGGER.debug("findAllProductDTOs()");
        return productDao.findAllProductDTOs().collect(Collectors.toList());
    }

    /**
     * Returns all {@code product Data Transfer Objects} that
     * matches given category id
     *
     * @param categoryId category id to find {@code product Data Transfer Objects}s by
     * @return {@code List} of a {@code product Data Transfer Objects}s
     */
    @Override
    public List<ProductDTO> findProductDTOsByCategoryId(Integer categoryId) {

        LOGGER.debug("findProductDTOsByCategoryId({})", categoryId);
        return productDao.findProductDTOsByCategoryId(categoryId).collect(Collectors.toList());
    }

    /**
     * Returns all {@code product Data Transfer Objects} that
     * matches given filter
     *
     * @param filter filter to find {@code product Data Transfer Objects}s by
     * @return {@code List} of a {@code product Data Transfer Objects}s
     */
    @Override
    public List<ProductDTO> findProductDTOsByFilter(Filter filter) {

        LOGGER.debug("findProductDTOsByFilter({})", filter);

        Stream<ProductDTO> productDTOs;

        if(filter.getCategoryId() == null) {
            productDTOs = productDao.findProductDTOsFromDateInterval(filter.getDateBegin(), filter.getDateEnd());
        } else {
            productDTOs = productDao
                    .findProductDTOsByMixedFilter(filter.getDateBegin(), filter.getDateEnd(), filter.getCategoryId());
        }

        return productDTOs.collect(Collectors.toList());
    }

    /**
     * Saves single {@code product} to a storage
     *
     * @param product {@code product} to be saved
     * @return just saved {@code product} with given id
     */
    @Override
    public Product add(Product product) {

        LOGGER.debug("add({})", product);

        product.setDateAdded(LocalDate.now());

        return productDao.add(product)
                .orElseThrow(() -> new RuntimeException("Failed to add product to DB"));
    }

    /**
     * Updates already existing {@code product} by new one
     *
     * @param product {@code product} to update older one
     */
    @Override
    public void update(Product product) {

        LOGGER.debug("update({})", product);
        productDao.update(product);
    }

    /**
     * Deletes existing {@code product} with given product id
     *
     * @param productId product id to delete {@code product}
     */
    @Override
    public void delete(Integer productId) {

        LOGGER.debug("delete({})", productId);
        productDao.delete(productId);
    }
}
