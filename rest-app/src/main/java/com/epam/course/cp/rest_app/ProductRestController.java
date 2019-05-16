package com.epam.course.cp.rest_app;

import com.epam.course.cp.dto.Filter;
import com.epam.course.cp.dto.ProductDTO;
import com.epam.course.cp.model.Product;
import com.epam.course.cp.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 *Controller used to operate with {@code Products}.
 *
 * <p>
 * This controller was built under
 * <a href="https://en.wikipedia.org/wiki/Representational_state_transfer">
 * REST
 * </a>
 * technology
 * </p>
 *
 * @see RestController
 * @see Product
 * @see ProductDTO
 * @see ProductService
 * @author Maksim Martsiusheu
 */
@RestController
@RequestMapping(value = "/products")
public class ProductRestController {

    /**
     * Default logger for current class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);

    /**
     * Service layer object to get information of products
     */
    private ProductService productService;

    /**
     * Constructs new object with given service layer object
     *
     * @param productService product service layer object
     */
    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }


    /**
     * Returns all {@code products} found
     *
     * @return {@code List} of a {@code products}
     */
    @GetMapping(value = "")
    public List<Product> findAll() {

        LOGGER.debug("get all products");
        return productService.findAll();
    }

    /**
     * Returns a {@code product} with given id
     *
     * @param id id of a {@code product} to find by
     * @return single {@code product}
     */
    @GetMapping(value = "/{id}")
    public Product findById(@PathVariable() Integer id) {

        LOGGER.debug("get product by id = {}", id);
        return productService.findById(id);
    }

    /**
     * Returns all {@code product Data Transfer Objects} found
     *
     * @return {@code List} of a {@code product Data Transfer Objects}s
     */
    @GetMapping(value = "/info")
    public List<ProductDTO> findAllProductDTOs() {

        LOGGER.debug("get all productDTOs");
        return productService.findAllProductDTOs();
    }

    /**
     * Returns all {@code product Data Transfer Objects} that
     * matches given request params
     *
     * @param dateBegin Date describing beginning of date interval
     * @param dateEnd Date describing ending of date interval
     * @param id Category id to select product DTOs by
     * @return {@code List} of a {@code product Data Transfer Objects}s
     */
    @GetMapping(value = "/filter")
    public List<ProductDTO> findProductDTOsByMixedFilter(
            @RequestParam(value = "from", defaultValue = "1970-01-01")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateBegin,
            @RequestParam(value = "to", defaultValue = "3000-01-01")
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateEnd,
            @RequestParam(value = "id", required = false) Integer id) {

        LOGGER.debug("findProductDTOsBYMixedFilter({},{},{})", dateBegin, dateEnd, id);

        Filter filter = new Filter();
        filter.setDateBegin(dateBegin);
        filter.setDateEnd(dateEnd);
        filter.setCategoryId(id);

        return productService.findProductDTOsByFilter(filter);
    }

    /**
     * Saves single {@code product} to a storage
     *
     * @param product {@code product} to be saved
     * @return just saved {@code product} with given id
     */
    @PostMapping(value = "")
    public Product add(@RequestBody Product product) {

        LOGGER.debug("add({})", product);
        return productService.add(product);
    }

    /**
     * Updates already existing {@code product} by new one
     *
     * @param product {@code product} to update older one
     */
    @PutMapping(value = "/{id}")
    public void update(@RequestBody Product product) {

        LOGGER.debug("update({})", product);
        productService.update(product);
    }

    /**
     * Deletes existing {@code product} with given product id
     *
     * @param id product id to delete {@code product}
     */
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id) {

        LOGGER.debug("delete({})", id);
        productService.delete(id);
    }
}
