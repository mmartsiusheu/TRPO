package com.epam.course.cp.service;

import com.epam.course.cp.dao.CategoryDao;
import com.epam.course.cp.dto.CategoryDTO;
import com.epam.course.cp.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of {@code CategoryService} interface
 *
 * @see Category
 * @see CategoryDTO
 * @see CategoryDao
 * @see Optional
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    /**
     * Default logger for current class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    /**
     * Data access layer object to execute task to data source
     */
    private final CategoryDao categoryDao;

    /**
     * Constructs new object with given data access layer object
     *
     * @param categoryDao category data access layer object
     */
    @Autowired
    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    /**
     * Returns {@code Category} with given id
     *
     * @param categoryId category id to find {@code category} by
     * @return {@code} Category that fits given id
     */
    @Override
    public Category findById(Integer categoryId) {

        LOGGER.debug("findById({})", categoryId);
        return categoryDao.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Failed to get category from DB"));
    }

    /**
     * Returns all {@code CategoryDTOs}
     *
     * @return {@code List} describing all category DTOs found
     */
    @Override
    public List<CategoryDTO> findAllCategoryDTOs() {

        LOGGER.debug("findAllCategoryDTOs()");
        return categoryDao.findAllCategoryDTOs().collect(Collectors.toList());
    }

    /**
     * Returns {@code Category DTO} with given category id
     *
     * @param categoryId category id to category DTOs by
     * @return {@code Category DTO} that fits given id
     */
    @Override
    public CategoryDTO findCategoryDTOById(Integer categoryId) {

        LOGGER.debug("findCategoryDTOById({})", categoryId);
        return categoryDao.findCategoryDTOById(categoryId)
                .orElseThrow(() -> new RuntimeException("Failed to get categoryDTO from DB"));
    }

    /**
     * Returns all sub{@code Categories} found
     *
     * @return {@code List} representing sub {@code Categories} found
     */
    @Override
    public List<Category> findAllSubCategories() {

        LOGGER.debug("findAllSubCategories()");
        return categoryDao.findAllSubCategories().collect(Collectors.toList());
    }

    /**
     * Returns all sub {@code Categories DTOs} with given category id
     *
     * @param categoryId category id to find sub category DTOs by
     * @return {@code List} representing {@code CategoryDTOs} that fits given category id
     */
    @Override
    public List<CategoryDTO> findSubCategoryDTOsByCategoryId(Integer categoryId) {

        LOGGER.debug("findSubCategoryDTOsByCategoryId({})", categoryId);
        return categoryDao.findSubCategoryDTOsByCategoryId(categoryId).collect(Collectors.toList());
    }

    /**
     * Saves given category to data storage
     *
     * @param category {@code category} to save
     * @return just save category with generated id
     */
    @Override
    public Category add(Category category) {

        LOGGER.debug("add({})", category);
        return categoryDao.add(category)
                .orElseThrow(() -> new RuntimeException("Failed to add category to DB"));
    }

    /**
     * Updates already existing {@code category} with new one
     *
     * @param category {@code category} to update older one
     */
    @Override
    public void update(Category category) {

        LOGGER.debug("update({})", category);
        categoryDao.update(category);

    }

    /**
     * Deletes {@code Category} with given id
     *
     * @param categoryId category id to delete by
     */
    @Override
    public void delete(Integer categoryId) {

        LOGGER.debug("delete({})", categoryId);
        categoryDao.delete(categoryId);
    }

    /**
     * Finds all {@code categories} that are parents for given id
     *
     * @param id id to find parents
     * @return {@code List} representing parent {@code categories} found
     */
    @Override
    public List<Category> findAllPossibleParentsForId(Integer id) {

        LOGGER.debug("findAllPossibleParentsForId({})", id);

        //if it's top level category (i.e. parent == null)
        Optional<Category> currentCategory = categoryDao.findById(id);
        if (currentCategory.isPresent() && currentCategory.get().getParentId() == 0) {
            //and it has subcategories
            Optional<CategoryDTO> anySubcategory = categoryDao.findSubCategoryDTOsByCategoryId(id).findAny();
            if (anySubcategory.isPresent()) {
                //then the category cannot change its parent
                return Collections.emptyList();
            }
        }

        return categoryDao.findAllPossibleParentsForId(id).collect(Collectors.toList());
    }

    /**
     * Finds all parents {@code categories}
     *
     * @return {@code List} representing all parent {@code categories}
     */
    @Override
    public List<Category> findAllPossibleParents() {

        LOGGER.debug("findAllParents()");

        return categoryDao.findAllPossibleParents().collect(Collectors.toList());
    }
}
