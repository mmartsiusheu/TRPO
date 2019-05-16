package com.epam.course.cp.dao;

import com.epam.course.cp.dto.CategoryDTO;
import com.epam.course.cp.model.Category;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Methods for direct access to data source
 *
 * @see Optional
 * @see Stream
 * @see Category
 * @see CategoryDTO
 * @author Maksim Martsiusheu
 */
public interface CategoryDao {

    /**
     * Returns all categories entities in data source.
     *
     * @return Categories as Stream
     */
    Stream<Category> findAll();

    /**
     * Returns category with defined id.
     *
     * @param categoryId Id of element to find
     * @return {@code Optional} describing the value of category found
     */
    Optional<Category> findById(Integer categoryId);

    /**
     * Returns all sub-categories found in data source.
     *
     * @return Sub-categories as {@code Stream}
     */
    Stream<Category> findAllSubCategories();

    /**
     * Returns all DataTransferObjects(DTO) of categories found in data source.
     *
     * @return Category Data Transfer Objects as {@code Stream}
     */
    Stream<CategoryDTO> findAllCategoryDTOs();

    /**
     * Returns  DataTransferObject(DTO) of category found in data source by id.
     *
     * @param id Id of element to find
     * @return {@code Optional} describing the value of category's DTO found
     */
    Optional<CategoryDTO> findCategoryDTOById(Integer id);

    /**
     * Returns  DataTransferObject(DTO) of sub-category found in data source by category id.
     *
     * @param id category id to find sub-categories by
     * @return Sub-category Data Transfer Objects as {@code Stream}
     */
    Stream<CategoryDTO> findSubCategoryDTOsByCategoryId(Integer id);

    /**
     * Save category to data source. Returns inserted Category with generated id.
     *
     * @param category Category object to save in data source
     * @return {@code Optional} category with generated id
     */
    Optional<Category> add(Category category);

    /**
     * Update already existing category object by new object.
     *
     * @param category Object to replace older
     */
    void update(Category category);

    /**
     * Remove category from data source by defined id.
     *
     * @param categoryId Category id to delete
     */
    void delete(Integer categoryId);

    /**
     * Returns all possible categories that are parents for the given id.
     *
     * @param id id to find parent categories
     * @return Categories found as {@code Stream}
     */
    Stream<Category> findAllPossibleParentsForId(Integer id);

    Stream<Category> findAllPossibleParents();
}
