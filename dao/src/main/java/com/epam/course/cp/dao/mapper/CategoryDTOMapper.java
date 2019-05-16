package com.epam.course.cp.dao.mapper;

import com.epam.course.cp.dto.CategoryDTO;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Category Data Transfer Object Mapper
 *
 * @see RowMapper
 * @see CategoryDTO
 * @author Maksim Martsiusheu
 */
@Component
public class CategoryDTOMapper implements RowMapper<CategoryDTO> {

    /**
     * Category Data Transfer Object id query parameter name
     */
    public final static String CATEGORY_DTO_ID = "category_id";

    /**
     * Category Data Transfer Object name query parameter name
     */
    public final static String CATEGORY_DTO_NAME = "category_name";

    /**
     * Category Data Transfer Object parent id query parameter name
     */
    public final static String CATEGORY_DTO_PARENT_ID = "parent_id";

    /**
     * Category Data Transfer Object product amount query parameter name
     */
    public final static String CATEGORY_DTO_PRODUCT_AMOUNT = "product_amount";

    /**
     * Map values from sql result set to category Data Transfer Object(DTO)
     *
     * @param resultSet sql result set with necessary values
     * @param i number of rows in result set
     * @return New Category DTO object
     * @throws SQLException If can't extract values from result set
     */
    @Override
    public CategoryDTO mapRow(ResultSet resultSet, int i) throws SQLException {

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(resultSet.getInt(CATEGORY_DTO_ID));
        categoryDTO.setCategoryName(resultSet.getString(CATEGORY_DTO_NAME));
        categoryDTO.setParentId(resultSet.getInt(CATEGORY_DTO_PARENT_ID));
        categoryDTO.setProductsAmount(resultSet.getInt(CATEGORY_DTO_PRODUCT_AMOUNT));

        return categoryDTO;
    }
}
