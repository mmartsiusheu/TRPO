package com.epam.course.cp.dao;

import com.epam.course.cp.dao.exception.DaoRuntimeException;
import com.epam.course.cp.dao.mapper.ProductDTOMapper;
import com.epam.course.cp.dao.mapper.ProductMapper;
import com.epam.course.cp.dto.ProductDTO;
import com.epam.course.cp.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Methods for direct access to data source using JDBC driver
 *
 * @see Optional
 * @see Stream
 * @see Product
 * @see ProductDTO
 * @author Maksim Martsiusheu
 */
@Repository
public class ProductDaoJdbcImpl implements ProductDao {

    /**
     * Default logger for current class
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductDaoJdbcImpl.class);

    /**
     * Jdbc template to execute actions to data source
     */
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Category mapper to create java object from result set
     */
    private final ProductMapper productMapper;

    /**
     * Category DTOs mapper to create java object from result set
     */
    private final ProductDTOMapper productDTOMapper;

    /**
     * Sql statement to select all products
     */
    @Value("${product.selectAll}")
    private String getAllProductsSql;

    /**
     * Sql statement to select product by id
     */
    @Value("${product.selectById}")
    private String getProductByIdSql;

    /**
     * Sql statement to select all products Data Transfer Objects(DTO)
     */
    @Value("${productDTO.selectAll}")
    private String getAllProductDTOsSql;

    /**
     * Sql statement to select all products Data Transfer Objects by category id
     */
    @Value("${productDTO.selectByCategoryId}")
    private String getProductDTOsByCategorySql;

    /**
     * Sql statement to select all products Data Transfer Objects(DTO) that fits date interval
     */
    @Value("${productDTO.selectFromDateInterval}")
    private String getProductDTOsFromDateIntervalSql;

    /**
     * Sql statement to select all products Data Transfer Objects(DTO) that fits date interval and category id
     */
    @Value("${productDTO.selectByMixedFilter}")
    private String getProductDTOsByMixedFilterSql;

    /**
     * Sql statement to save product in data source
     */
    @Value("${product.insert}")
    private String insertProductSql;

    /**
     * Sql statement to update already existing product
     */
    @Value("${product.update}")
    private String updateProductSql;

    /**
     * Sql statement to remove product from data source
     */
    @Value("${product.delete}")
    private String deleteProductSql;

    /**
     * Begin date query parameter name
     */
    private static final String DATE_INTERVAL_BEGIN = "date_begin";

    /**
     * End date query parameter name
     */
    private static final String DATE_INTERVAL_END = "date_end";

    /**
     * Construct productDaoJdbcImpl
     *
     * @param namedParameterJdbcTemplate jdbc template to inject
     * @param productMapper product mapper to inject
     * @param productDTOMapper product dto mapper to inject
     */
    @Autowired
    public ProductDaoJdbcImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                              ProductMapper productMapper,
                              ProductDTOMapper productDTOMapper) {

        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.productMapper = productMapper;
        this.productDTOMapper = productDTOMapper;
    }

    /**
     * Returns all products
     *
     * @return Product as {@code Stream}
     */
    @Override
    public Stream<Product> findAll() {

        LOGGER.debug("findAll()");

        List<Product> productList = namedParameterJdbcTemplate.query(getAllProductsSql, productMapper);
        return productList.stream();
    }

    /**
     * Returns product with defined id
     *
     * @param productId Id of element to find
     * @return {@code Optional} describing product found
     */
    @Override
    public Optional<Product> findById(Integer productId) {

        LOGGER.debug("findById()", productId);

        MapSqlParameterSource namedParameters = new MapSqlParameterSource(ProductMapper.PRODUCT_ID, productId);
        Product product = namedParameterJdbcTemplate.queryForObject(getProductByIdSql, namedParameters, productMapper);

        return Optional.ofNullable(product);
    }

    /**
     * Returns all products Data Transfer Objects(DTO)
     *
     * @return Product DTOs as {@code Stream}
     */
    @Override
    public Stream<ProductDTO> findAllProductDTOs() {

        LOGGER.debug("findAllProductsDTOs()");

        List<ProductDTO> productDTOList = namedParameterJdbcTemplate.query(getAllProductDTOsSql, productDTOMapper);
        return productDTOList.stream();
    }

    /**
     * Returns product Data Transfer Object by defined category id
     *
     * @param categoryId Category id to find product DTOs by
     * @return Product DTOs as {@code Stream}
     */
    @Override
    public Stream<ProductDTO> findProductDTOsByCategoryId(Integer categoryId) {

        LOGGER.debug("findProductDTOsByCategory({})", categoryId);

        MapSqlParameterSource namedParameters = new MapSqlParameterSource(ProductDTOMapper.PRODUCT_DTO_CATEGORY_ID, categoryId);
        List<ProductDTO> productDTOList = namedParameterJdbcTemplate.query(getProductDTOsByCategorySql, namedParameters, productDTOMapper);

        return productDTOList.stream();
    }

    /**
     * Returns all product Data Transfer Objects that fits date interval
     *
     * @param dateBegin Date describing beginning of date interval
     * @param dateEnd Date describing ending of date interval
     * @return Product DTOs as {@code Stream}
     */
    @Override
    public Stream<ProductDTO> findProductDTOsFromDateInterval(LocalDate dateBegin, LocalDate dateEnd) {

        LOGGER.debug("findProductDTOsFromDateInterval({}, {})", dateBegin, dateEnd);

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue(DATE_INTERVAL_BEGIN, dateBegin);
        namedParameters.addValue(DATE_INTERVAL_END, dateEnd);

        List<ProductDTO> productDTOList = namedParameterJdbcTemplate.query(getProductDTOsFromDateIntervalSql, namedParameters, productDTOMapper);

        return productDTOList.stream();
    }

    /**
     * Returns all product Data Transfer Objects that fits date interval and caregory id
     *
     * @param dateBegin Date describing beginning of date interval
     * @param dateEnd Date describing ending of date interval
     * @param categoryId Category id to select product DTOs by
     * @return Product DTOs as {@code Stream}
     */
    @Override
    public Stream<ProductDTO> findProductDTOsByMixedFilter(LocalDate dateBegin, LocalDate dateEnd, Integer categoryId) {

        LOGGER.debug("findProductDTOsByMixedFilter({}, {}, {})", dateBegin, dateEnd, categoryId);
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue(DATE_INTERVAL_BEGIN, dateBegin);
        namedParameters.addValue(DATE_INTERVAL_END, dateEnd);
        namedParameters.addValue(ProductDTOMapper.PRODUCT_DTO_CATEGORY_ID, categoryId);

        List<ProductDTO> productDTOList = namedParameterJdbcTemplate.query(getProductDTOsByMixedFilterSql, namedParameters, productDTOMapper);
        return productDTOList.stream();

    }

    /**
     * Save product to data source
     *
     * @param product Product object to save in data source
     * @return {@code Optional} describing saved product with generated id
     */
    @Override
    public Optional<Product> add(Product product) {

        LOGGER.debug("add({})", product);

        MapSqlParameterSource namedParameters = getProductSqlParametersSource(product);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(insertProductSql, namedParameters, keyHolder);

        product.setProductId(keyHolder.getKey().intValue());
        return Optional.of(product);
    }

    /**
     * Update already existing product
     *
     * @param product Object to replace older
     */
    @Override
    public void update(Product product) {

        LOGGER.debug("update({})", product);

        MapSqlParameterSource namedParameters = getProductSqlParametersSource(product);
        namedParameters.addValue(ProductMapper.PRODUCT_ID, product.getProductId());

        Optional.of(namedParameterJdbcTemplate.update(updateProductSql, namedParameters))
                .filter(this::successfullyUpdate)
                .orElseThrow(() -> new DaoRuntimeException("Failed to update product in DB"));
    }

    /**
     * Delete product from data source by product id
     * @param productId Product id to delete
     */
    @Override
    public void delete(Integer productId) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource(ProductMapper.PRODUCT_ID, productId);

        Optional.of(namedParameterJdbcTemplate.update(deleteProductSql, namedParameters))
                .filter(this::successfullyUpdate)
                .orElseThrow(() -> new DaoRuntimeException("Failed to delete product in DB"));
    }

    private boolean successfullyUpdate(Integer numRowsUpdated) {
        return numRowsUpdated > 0;
    }

    private MapSqlParameterSource getProductSqlParametersSource(Product product) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue(ProductMapper.PRODUCT_NAME, product.getProductName());
        namedParameters.addValue(ProductMapper.PRODUCT_AMOUNT, product.getProductAmount());
        namedParameters.addValue(ProductMapper.DATE_ADDED, product.getDateAdded());
        namedParameters.addValue(ProductMapper.PRODUCT_CATEGORY_ID, product.getCategoryId());

        return namedParameters;
    }
}
