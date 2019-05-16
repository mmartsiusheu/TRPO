package com.epam.course.cp.dao.mapper;

import com.epam.course.cp.dto.ProductDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Product Data Transfer Object Mapper
 *
 * @see RowMapper
 * @see ProductDTO
 * @author Maksim Martsiusheu
 */
@Component
public class ProductDTOMapper implements RowMapper<ProductDTO> {

    /**
     * Product Data Transfer Object id query parameter name
     */
    public static final String PRODUCT_DTO_ID = "prod_id";

    /**
     * Product Data Transfer Object category name query parameter name
     */
    public static final String PRODUCT_DTO_CATEGORY_NAME = "category_name";

    /**
     * Product Data Transfer Object sub category name query parameter name
     */
    public static final String PRODUCT_DTO_SUBCATEGORY_NAME = "subcategory_name";

    /**
     * Product Data Transfer Object name query parameter name
     */
    public static final String PRODUCT_DTO_NAME = "prod_name";

    /**
     * Product Data Transfer Object amount query parameter name
     */
    public static final String PRODUCT_DTO_AMOUNT = "prod_amount";

    /**
     * Product Data Transfer Object date added query parameter name
     */
    public static final String PRODUCT_DTO_DATE_ADDED = "date_added";

    /**
     * Product Data Transfer Object category id query parameter name
     */
    public static final String PRODUCT_DTO_CATEGORY_ID = "category_id";

    /**
     * Map values from sql result set to product Data Transfer Object(DTO)
     *
     * @param resultSet sql result set with necessary values
     * @param i number of rows in result set
     * @return New Product DTO object
     * @throws SQLException If can't extract values from result set
     */
    @Override
    public ProductDTO mapRow(ResultSet resultSet, int i) throws SQLException {

        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductId(resultSet.getInt(PRODUCT_DTO_ID));
        productDTO.setCategoryName(resultSet.getString(PRODUCT_DTO_CATEGORY_NAME));
        productDTO.setSubCategoryName(resultSet.getString(PRODUCT_DTO_SUBCATEGORY_NAME));
        productDTO.setProductName(resultSet.getString(PRODUCT_DTO_NAME));
        productDTO.setProductAmount(resultSet.getInt(PRODUCT_DTO_AMOUNT));
        productDTO.setDateAdded(resultSet.getDate(PRODUCT_DTO_DATE_ADDED).toLocalDate());
        productDTO.setCategoryId(resultSet.getInt(PRODUCT_DTO_CATEGORY_ID));

        return productDTO;
    }
}
