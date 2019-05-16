package com.epam.course.cp.web_app;

import com.epam.course.cp.model.Category;
import com.epam.course.cp.service.CategoryService;
import com.epam.course.cp.web_app.validator.CategoryValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * Controller used to operate with {@code Categories}.
 * <p>
 * All of methods of this controller return template name
 * or redirect command as {@code String}
 *
 * @author Maksim Martsiusheu
 * @see Controller
 * @see Model
 * @see BindingResult
 * @see Category
 * @see CategoryValidator
 * @see CategoryService
 */
@Controller
public class CategoryController {

    /**
     * Default logger for current class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    /**
     * Service layer object to get information of categories
     */
    private final CategoryService categoryService;

    /**
     * Object to validate product
     */
    private final CategoryValidator categoryValidator;

    /**
     * Constructs new object with given params
     *
     * @param categoryService   category service layer object to inject
     * @param categoryValidator object for validate category to inject
     */
    @Autowired
    public CategoryController(CategoryService categoryService, CategoryValidator categoryValidator) {
        this.categoryService = categoryService;
        this.categoryValidator = categoryValidator;
    }

    /**
     * Go to page with list of all categories
     *
     * @param model model to storage information for view rendering
     * @return view template name
     */
    @GetMapping(value = "/categories")
    public final String categories(Model model) {

        LOGGER.debug("find allCategoryDTOs({})", model);
        model.addAttribute("categories", categoryService.findAllCategoryDTOs());
        model.addAttribute("location", "categories");
        return "categories";

    }

    /**
     * Go to page with list of all sub-categories parents of which
     * has present id
     *
     * @param id    id of parant category
     * @param model model to storage information for view rendering
     * @return view template name
     */
    @GetMapping(value = "/categories/info/{id}/subs")
    public final String subCategoriesByParentId(@PathVariable Integer id, Model model) {

        LOGGER.debug("findSubCategoriesByCategoryId({}, {})", id, model);
        model.addAttribute("subcategories", categoryService.findSubCategoryDTOsByCategoryId(id));
        model.addAttribute("location", "categories");
        return "subcategories-table";
    }

    /**
     * Go to page for adding new category
     *
     * @param model model to storage information for view rendering
     * @return view template name
     */
    @GetMapping(value = "/category")
    public final String gotoAddCategory(Model model) {

        LOGGER.debug("gotoAddCategory()");
        Category category = new Category();
        model.addAttribute("isNew", true);
        model.addAttribute("category", category);
        model.addAttribute("parentCategories", categoryService.findAllPossibleParents());
        model.addAttribute("location", "categories");

        return "category";
    }

    /**
     * Save new {@code product} if it is valid
     * and redirect to page with list of products
     *
     * @param category category for saving
     * @param result   result object to storage information about product validation
     * @param model    model to storage information for view rendering
     * @return view template name or redirect to another uri
     */
    @PostMapping(value = "/category")
    public final String addCategory(@Valid Category category, BindingResult result, Model model) {

        LOGGER.debug("addCategory({})", model);

        categoryValidator.validate(category, result);
        if (result.hasErrors()) {
            model.addAttribute("parentCategories", categoryService.findAllPossibleParents());
            return "category";
        } else {
            categoryService.add(category);
            return "redirect:/categories";
        }
    }

    /**
     * Go to page for updating information about category
     *
     * @param id category id for update
     * @param model model to storage information for view rendering
     * @return view template name
     */
    @GetMapping(value = "/category/{id}")
    public final String gotoUpdateCategory(@PathVariable Integer id, Model model) {

        LOGGER.debug("gotoUpdateCategory({}, {})", id, model);
        model.addAttribute("isNew", false);
        model.addAttribute("category", categoryService.findById(id));
        model.addAttribute("productsAmount", categoryService.findCategoryDTOById(id).getProductsAmount());
        model.addAttribute("parentCategories", categoryService.findAllPossibleParentsForId(id));
        model.addAttribute("location", "categories");
        return "category";
    }

    /**
     * Updates already existing {@code category} by new one if it is valid
     * and redirect to page with list of categories
     *
     * @param category category for update
     * @param result result object to storage information about validation
     * @param model model to storage information for view rendering
     * @return view template name or redirect to another uri
     */
    @PostMapping(value = "/category/{id}")
    public final String updateCategory(@Valid Category category, BindingResult result, Model model) {

        LOGGER.debug("updateCategory({}, {})", category, result);

        categoryValidator.validate(category, result);

        if (result.hasErrors()) {
            model.addAttribute("productsAmount", categoryService.findCategoryDTOById(category.getCategoryId())
                    .getProductsAmount());
            model.addAttribute("parentCategories", categoryService.findAllPossibleParentsForId(category.getCategoryId()));
            return "category";
        } else {
            categoryService.update(category);
            return "redirect:/categories";
        }
    }

    /**
     * Delete category by id
     *
     * @param id category id to delete
     * @return redirect to another uri
     */
    @GetMapping(value = "/categories/{id}")
    public final String deleteCategory(@PathVariable Integer id, Model model) {

        LOGGER.debug("delete");
        categoryService.delete(id);
        return "redirect:/categories";
    }


}
