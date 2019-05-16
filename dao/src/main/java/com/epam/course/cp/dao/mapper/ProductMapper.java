package com.epam.course.cp.dao.mapper;

import com.epam.course.cp.model.Product;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Product object Mapper
 *
 * @see RowMapper
 * @see Product
 * @author Maksim Martsiusheu
 */
@Component
public class ProductMapper implements RowMapper<Product> {

    /**
     * Product id query parameter name
     */
    public static final String PRODUCT_ID = "prod_id";

    /**
     * Product product name query parameter name
     */
    public static final String PRODUCT_NAME = "prod_name";

    /**
     * Product product amount query parameter name
     */
    public static final String PRODUCT_AMOUNT = "prod_amount";

    /**
     * Product date added query parameter name
     */
    public static final String DATE_ADDED = "date_added";

    /**
     * Product category id query parameter name
     */
    public static final String PRODUCT_CATEGORY_ID = "category_id";

    /**
     * Map values from sql result set to product object
     *
     * @param resultSet sql result set with necessary values
     * @param i number of rows in result set
     * @return New Product object
     * @throws SQLException If can't extract values from result set
     */
    @Override
    public Product mapRow(ResultSet resultSet, int i) throws SQLException {

        Product product = new Product();

        product.setProductId(resultSet.getInt(PRODUCT_ID));
        product.setProductName(resultSet.getString(PRODUCT_NAME));
        product.setProductAmount(resultSet.getInt(PRODUCT_AMOUNT));
        product.setDateAdded(resultSet.getDate(DATE_ADDED).toLocalDate());
        product.setCategoryId(resultSet.getInt(PRODUCT_CATEGORY_ID));

        return product;
    }
}
