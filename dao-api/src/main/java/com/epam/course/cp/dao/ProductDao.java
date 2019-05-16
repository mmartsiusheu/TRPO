package com.epam.course.cp.dao;

import com.epam.course.cp.dto.ProductDTO;
import com.epam.course.cp.model.Product;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Methods for direct access to data source
 *
 * @see Optional
 * @see Stream
 * @see Product
 * @see ProductDTO
 * @author Maksim Martsiusheu
 */
public interface ProductDao {

    /**
     * Returns all products entities in data source.
     *
     * @return products as Stream
     */
    Stream<Product> findAll();

    /**
     * Returns product with defined id.
     *
     * @param productId Id of element to find
     * @return {@code Optional} describing the value of product found
     */
    Optional<Product> findById(Integer productId);

    /**
     * Returns all DataTransferObjects(DTO) of products found in data source.
     *
     * @return Product Data Transfer Objects as {@code Stream}
     */
    Stream<ProductDTO> findAllProductDTOs();

    /**
     * Returns  DataTransferObject(DTO) of products found in data source by category id.
     *
     * @param categoryId Category id to find product DTOs by
     * @return Product Data Transfer Objects as {@code Stream}
     */
    Stream<ProductDTO> findProductDTOsByCategoryId(Integer categoryId);

    /**
     * Returns Data Transfer Objects(DTO) of products selected by date interval.
     *
     * @param dateBegin Date describing beginning of date interval
     * @param dateEnd Date describing ending of date interval
     * @return All product DTOs that fits date interval as {@code Stream}
     */
    Stream<ProductDTO> findProductDTOsFromDateInterval(LocalDate dateBegin, LocalDate dateEnd);

    /**
     * Returns Data Transfer Objects(DTO) of products selected by date interval and by category id.
     *
     * @param dateBegin Date describing beginning of date interval
     * @param dateEnd Date describing ending of date interval
     * @param categoryId Category id to select product DTOs by
     * @return All product DTOs that fits date interval as {@code Stream}
     */
    Stream<ProductDTO> findProductDTOsByMixedFilter(LocalDate dateBegin, LocalDate dateEnd, Integer categoryId);

    /**
     * Save product to data source. Returns inserted Product with generated id.
     *
     * @param product Product object to save in data source
     * @return {@code Optional} Product with generated id
     */
    Optional<Product> add(Product product);

    /**
     * Update already existing product object by new object.
     *
     * @param product Object to replace older
     */
    void update(Product product);

    /**
     * Remove product from data source by defined id.
     *
     * @param productId Product id to delete
     */
    void delete(Integer productId);
}
