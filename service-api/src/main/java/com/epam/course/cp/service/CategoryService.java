package com.epam.course.cp.service;

import com.epam.course.cp.dto.CategoryDTO;
import com.epam.course.cp.model.Category;

import java.util.List;

/**
 * Service methods for work with {@code categories}
 * and {@code category Data Transfer Objects}
 *
 * @see Category
 * @see CategoryDTO
 * @author Maksim Martsiusheu
 */
public interface CategoryService {

    /**
     * Returns {@code category} with given id
     *
     * @param categoryId category id to find {@code category} by
     * @return single {@code category} with given id
     */
    Category findById(Integer categoryId);

    /**
     * Returns {@code category Data Transfer Object} with given category id
     *
     * @param categoryId category id to category DTOs by
     * @return single category DTOs
     */
    CategoryDTO findCategoryDTOById(Integer categoryId);

    /**
     * Returns all existing sub {@code categories}
     *
     * @return {@code List} representing all sub {@code categories}
     */
    List<Category> findAllSubCategories();

    /**
     * Returns all existing {@code categories Data Transfer Objects}
     *
     * @return {@code List} representing all category DTOs
     */
    List<CategoryDTO> findAllCategoryDTOs();

    /**
     * Returns all sub {@code category Data Transfer Object}s with given category id
     *
     * @param categoryId category id to find sub category DTOs by
     * @return {@code List} representing all sub category DTOs
     */
    List<CategoryDTO> findSubCategoryDTOsByCategoryId(Integer categoryId);

    /**
     * Saves {@code category} to storage
     *
     * @param category {@code category} to save
     * @return just saved {@code category} with given id
     */
    Category add(Category category);

    /**
     * Updates already existing {@code category} by new one
     *
     * @param category {@code category} to update older one
     */
    void update(Category category);

    /**
     * Deletes already existing {@code category} with given id
     *
     * @param categoryId category id to delete by
     */
    void delete(Integer categoryId);

    /**
     * Finds all {@code categories} that are parents besides category with given id
     *
     * @param id id to find parents
     * @return {@code List} representing parent {@code categories} found
     */
    List<Category> findAllPossibleParentsForId(Integer id);

    /**
     * Finds all parents {@code categories}
     *
     * @return {@code List} representing all parent {@code categories}
     */
    List<Category> findAllPossibleParents();
}
