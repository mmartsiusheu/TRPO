package com.epam.course.cp.dao.mapper;

import com.epam.course.cp.model.Category;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Category object Mapper
 *
 * @see RowMapper
 * @see Category
 * @author Maksim Martsiusheu
 */
@Component
public class CategoryMapper implements RowMapper<Category> {

    /**
     * Category id query parameter name
     */
    public static final String CATEGORY_ID = "category_id";

    /**
     * Category name query parameter name
     */
    public static final String CATEGORY_NAME = "category_name";

    /**
     * Category parent id query parameter name
     */
    public static final String CATEGORY_PARENT_ID = "parent_id";

    @Override
    public Category mapRow(ResultSet resultSet, int i) throws SQLException {

        Category category = new Category();

        category.setCategoryId(resultSet.getInt(CATEGORY_ID));
        category.setCategoryName(resultSet.getString(CATEGORY_NAME));
        category.setParentId(resultSet.getInt(CATEGORY_PARENT_ID));

        return category;

    }
}
