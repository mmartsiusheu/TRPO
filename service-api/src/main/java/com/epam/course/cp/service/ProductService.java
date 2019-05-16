package com.epam.course.cp.service;

import com.epam.course.cp.dto.Filter;
import com.epam.course.cp.dto.ProductDTO;
import com.epam.course.cp.model.Product;

import java.util.List;

/**
 * Service methods for work with {@code products}
 * and {@code product Data Transfer Objects}
 *
 * @see Product
 * @see ProductDTO
 * @author Maksim Martsiusheu
 */
public interface ProductService {

    /**
     * Returns all {@code products} found
     *
     * @return {@code List} of a {@code products}
     */
    List<Product> findAll();

    /**
     * Returns a {@code product} with given id
     *
     * @param productId id of a {@code product} to find by
     * @return single {@code product}
     */
    Product findById(Integer productId);

    /**
     * Returns all {@code product Data Transfer Objects} found
     *
     * @return {@code List} of a {@code product Data Transfer Objects}s
     */
    List<ProductDTO> findAllProductDTOs();

    /**
     * Returns all {@code product Data Transfer Objects} that
     * matches given category id
     *
     * @param categoryId category id to find {@code product Data Transfer Objects}s by
     * @return {@code List} of a {@code product Data Transfer Objects}s
     */
    List<ProductDTO> findProductDTOsByCategoryId(Integer categoryId);

    /**
     * Returns all {@code product Data Transfer Objects} that
     * matches given filter
     *
     * @param filter filter to find {@code product Data Transfer Objects}s by
     * @return {@code List} of a {@code product Data Transfer Objects}s
     */
    List<ProductDTO> findProductDTOsByFilter(Filter filter);

    /**
     * Saves single {@code product} to a storage
     *
     * @param product {@code product} to be saved
     * @return just saved {@code product} with given id
     */
    Product add(Product product);

    /**
     * Updates already existing {@code product} by new one
     *
     * @param product {@code product} to update older one
     */
    void update(Product product);

    /**
     * Deletes existing {@code product} with given product id
     *
     * @param productId product id to delete {@code product}
     */
    void delete(Integer productId);
}
