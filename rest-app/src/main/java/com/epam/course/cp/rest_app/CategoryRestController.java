package com.epam.course.cp.rest_app;

import com.epam.course.cp.dto.CategoryDTO;
import com.epam.course.cp.model.Category;
import com.epam.course.cp.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller used to operate with {@code Categories}.
 *
 * <p>
 * This controller was built under
 * <a href="https://en.wikipedia.org/wiki/Representational_state_transfer">
 * REST
 * </a>
 * technology
 * </p>
 *
 * @author Maksim Martsiusheu
 * @see RestController
 * @see Category
 * @see CategoryDTO
 * @see CategoryService
 */
@RestController
@RequestMapping(value = "/categories")
public class CategoryRestController {

    /**
     * Default logger for current class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryRestController.class);

    /**
     * Service layer object to get information of categories
     */
    private CategoryService categoryService;

    /**
     * Constructs new object with given service layer object
     *
     * @param categoryService category service layer object
     */
    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Returns all {@code CategoryDTOs}
     *
     * @return {@code List} describing all category DTOs found
     */
    @GetMapping(value = "/info")
    public List<CategoryDTO> findAllCategoryDTOs() {

        LOGGER.debug("get all categoryDTOs");
        return categoryService.findAllCategoryDTOs();
    }

    /**
     * Returns {@code Category} with given id
     *
     * @param id category id to find {@code category} by
     * @return {@code} Category that fits given id
     */
    @GetMapping(value = "/{id}")
    public Category findById(@PathVariable Integer id) {

        LOGGER.debug("get category by id = {}", id);
        return categoryService.findById(id);
    }

    /**
     * Returns all sub{@code Categories} found
     *
     * @return {@code List} representing sub {@code Categories} found
     */
    @GetMapping(value = "/subs")
    public List<Category> findAllSubCategories() {

        LOGGER.debug("findAllSubCategories");
        return categoryService.findAllSubCategories();
    }

    /**
     * Returns {@code Category DTO} with given category id
     *
     * @param id category id to category DTOs by
     * @return {@code Category DTO} that fits given id
     */
    @GetMapping(value = "/info/{id}")
    public CategoryDTO findCategoryDTOById(@PathVariable Integer id) {

        LOGGER.debug("indCategoryDTOById({})", id);
        return categoryService.findCategoryDTOById(id);
    }

    /**
     * Returns all sub {@code Categories DTOs} with given category id
     *
     * @param id category id to find sub category DTOs by
     * @return {@code List} representing {@code CategoryDTOs} that fits given category id
     */
    @GetMapping(value = "/info/{id}/subs")
    public List<CategoryDTO> findSubCategoryDTOsByCategoryId(@PathVariable Integer id) {

        LOGGER.debug("get subCategoryDTOs by category id = {}", id);
        return categoryService.findSubCategoryDTOsByCategoryId(id);
    }

    /**
     * Saves given category to data storage
     *
     * @param category {@code category} to save
     * @return just save category with generated id
     */
    @PostMapping
    public Category add(@RequestBody Category category) {

        LOGGER.debug("add category {}", category);
        return categoryService.add(category);
    }

    /**
     * Updates already existing {@code category} with new one
     *
     * @param category {@code category} to update older one
     * @return {@code ResponseEntity} representing work result
     */
    @PutMapping(value = "/{id}")
    public void update(@RequestBody Category category) {

        LOGGER.debug("update category in DB {}", category);
        categoryService.update(category);
    }

    /**
     * Deletes {@code Category} with given id
     *
     * @param id category id to delete by
     * @return {@code ResponseEntity} representing work result
     */
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id) {

        LOGGER.debug("delete category with id = {} from DB", id);
        categoryService.delete(id);
    }

    /**
     * Finds all parents {@code categories}
     *
     * @return {@code List} representing all parent {@code categories}
     */
    @GetMapping(value = "/possibleparents")
    public List<Category> findAllPossibleParents() {

        LOGGER.debug("findAllPossibleParents()");
        return categoryService.findAllPossibleParents();
    }

    /**
     * Finds all {@code categories} that are parents for given id
     *
     * @param id id to find parents
     * @return {@code List} representing parent {@code categories} found
     */
    @GetMapping(value = "/possibleparents/{id}")
    public List<Category> findAllPossibleParentsById(@PathVariable Integer id) {

        LOGGER.debug("indAllPossibleParentsById({})", id);
        return categoryService.findAllPossibleParentsForId(id);
    }
}
