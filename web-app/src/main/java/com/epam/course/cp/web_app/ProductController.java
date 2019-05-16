package com.epam.course.cp.web_app;

import com.epam.course.cp.dto.Filter;
import com.epam.course.cp.model.Product;
import com.epam.course.cp.service.CategoryService;
import com.epam.course.cp.service.ProductService;
import com.epam.course.cp.web_app.validator.FilterValidator;
import com.epam.course.cp.web_app.validator.ProductValidator;
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
 * Controller used to operate with {@code Products}.
 *
 *All of methods of this controller return template name
 * or redirect command as {@code String}
 *
 *
 * @author Maksim Martsiusheu
 * @see Controller
 * @see Model
 * @see BindingResult
 * @see Product
 * @see ProductValidator
 * @see CategoryService
 * @see ProductService
 */
@Controller
public class ProductController {

    /**
     * Default logger for current class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    /**
     * Service layer object to get information of products
     */
    private final ProductService productService;

    /**
     * Service layer object to get information of categories
     */
    private final CategoryService categoryService;

    /**
     * Object to validate product
     */
    private final ProductValidator productValidator;

    /**
     * Object to validate filter
     */
    private final FilterValidator filterValidator;

    /**
     * Constructs new object with given params
     *
     * @param productService product service layer object to inject
     * @param categoryService category service layer object to inject
     * @param productValidator object for validate product to inject
     * @param filterValidator object for validate filter to inject
     */
    @Autowired
    public ProductController(ProductService productService
            , CategoryService categoryService
            , ProductValidator productValidator
            , FilterValidator filterValidator) {

        this.productService = productService;
        this.categoryService = categoryService;
        this.productValidator = productValidator;
        this.filterValidator = filterValidator;
    }

    /**
     * Go to page for updating information about product
     *
     * @param id product id for update
     * @param model model to storage information for view rendering
     * @return view template name
     */
    @GetMapping(value = "/product/{id}")
    public final String gotoUpdateProduct(@PathVariable Integer id, Model model) {

        LOGGER.debug("go to update product with id ={}", id);

        model.addAttribute("product", productService.findById(id));
        model.addAttribute("isNew", false);
        model.addAttribute("categories", categoryService.findAllSubCategories());
        model.addAttribute("location", "products");
        return "product";
    }

    /**
     * Updates already existing {@code product} by new one if it is valid
     * and redirect to page with list of products
     *
     * @param product product for update
     * @param result result object to storage information about validation
     * @param model model to storage information for view rendering
     * @return view template name or redirect to another uri
     */
    @PostMapping(value = "/product/{id}")
    public final String updateProduct(@Valid Product product, BindingResult result, Model model) {

        LOGGER.debug("update product {}", product);

        productValidator.validate(product, result);
        model.addAttribute("categories", categoryService.findAllSubCategories());
        if (result.hasErrors()) {
            model.addAttribute(categoryService.findAllSubCategories());
            return "product";
        } else {
            productService.update(product);
            return "redirect:/products";
        }
    }

    /**
     * Go to page for adding new product
     *
     * @param model model to storage information for view rendering
     * @return view template name
     */
    @GetMapping(value = "/product")
    public final String gotoAddProduct(Model model) {

        LOGGER.debug("gotoAddProduct({})", model);
        Product product = new Product();
        model.addAttribute("isNew", true);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAllSubCategories());
        model.addAttribute("location", "products");
        return "product";
    }

    /**
     * Save new {@code product} if it is valid
     * and redirect to page with list of products
     *
     * @param product product for saving
     * @param result result object to storage information about product validation
     * @param model model to storage information for view rendering
     * @return view template name or redirect to another uri
     */
    @PostMapping(value = "/product")
    public final String addProduct(@Valid Product product, BindingResult result, Model model) {

        LOGGER.debug("addProduct({})", product);

        productValidator.validate(product, result);

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAllSubCategories());
            return "product";
        } else {
            productService.add(product);
            return "redirect:/products";
        }
    }

    /**
     * Go to page with list of all products
     *
     * @param filter default filter
     * @param model model to storage information for view rendering
     * @return view template name
     */
    @GetMapping(value = "/products")
    public final String products(Filter filter, Model model) {

        LOGGER.debug("find allProducts({})", model);
        model.addAttribute("filter", filter);
        model.addAttribute("categories", categoryService.findAllPossibleParents());
        model.addAttribute("products", productService.findProductDTOsByFilter(filter));
        model.addAttribute("location", "products");
        return "products";

    }

    /**
     *Go to page with list of products sorted by filter
     *
     * @param filter filter for searching products
     * @param result result object to storage information about filter validation
     * @param model model to storage information for view rendering
     * @return view template name
     */
    @PostMapping(value = "/products/filter")
    public final String productsByFilter(@Valid Filter filter, BindingResult result, Model model) {

        LOGGER.debug("findAllProductsDTOsFromDateInterval ({})", filter);
        model.addAttribute("categories", categoryService.findAllPossibleParents());
        model.addAttribute("location", "products");

        filterValidator.validate(filter, result);

        if (result.hasErrors()) {
            return "products";
        } else {
            model.addAttribute("products", productService.findProductDTOsByFilter(filter));
            return "products";
        }
    }

    /**
     * Delete product by id
     *
     * @param id product id to delete
     * @return redirect to another uri
     */
    @GetMapping(value = "/products/{id}")
    public final String deleteProduct(@PathVariable Integer id) {
        LOGGER.debug("delete product by id = {}", id);

        productService.delete(id);

        return "redirect:/products";
    }
}
