package com.epam.course.cp.dao;

import com.epam.course.cp.dao.exception.DaoRuntimeException;
import com.epam.course.cp.dao.mapper.CategoryDTOMapper;
import com.epam.course.cp.dao.mapper.CategoryMapper;
import com.epam.course.cp.dto.CategoryDTO;
import com.epam.course.cp.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Methods for direct access to data source using JDBC driver
 *
 * @see Optional
 * @see Stream
 * @see Category
 * @see CategoryDTO
 * @author Maksim Martsiusheu
 */
@Repository
public class CategoryDaoJdbcImpl implements CategoryDao {

    /**
     * Default logger for current class
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(CategoryDaoJdbcImpl.class);

    /**
     * Jdbc template to execute actions to data source
     */
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Category mapper to create java object from result set
     */
    private final CategoryMapper categoryMapper;

    /**
     * Category DTOs mapper to create java object from result set
     */
    private final CategoryDTOMapper categoryDTOMapper;

    /**
     * Sql statement to select all categories
     */
    @Value("${category.selectAll}")
    private String getAllCategoriesSql;

    /**
     * Sql statement to select all possible parents for  id
     */
    @Value("${category.selectParentsForId}")
    private String getAllPossibleParentsForIdSql;

    /**
     * Sql statement to select all possible parents
     */
    @Value("${category.selectParents}")
    private String getAllPossibleParentsSql;
    /**
     * Sql statement to select all sub-categories
     */
    @Value("${category.selectChildren}")
    private String getAllSubCategoriesSql;

    /**
     * Sql statement to select category by id
     */
    @Value("${category.selectById}")
    private String getCategoryByIdSql;

    /**
     * Sql statement to select category dto by id
     */
    @Value("${categoryDTO.selectCategoryDTOById}")
    private String getCategoryDTOByIdSql;

    /**
     * Sql statement to insert category to sql
     */
    @Value("${category.insert}")
    private String insertCategorySql;

    /**
     * Sql statement to update already existing category
     */
    @Value("${category.update}")
    private String updateCategorySql;

    /**
     * Sql statement to delete category
     */
    @Value("${category.delete}")
    private String deleteCategorySql;

    /**
     * Sql statement to select all category DTOs
     */
    @Value("${categoryDTO.selectAllCategoryDTOs}")
    private String getAllCategoryDTOsSql;

    /**
     * Sql statement to select all sub categories DTOs by category id
     */
    @Value("${subCategoryDTO.selectSubCategoryDTOsByCategoryId}")
    private String getSubCategoryDTOsByCategoryIdSql;

    /**
     * Construct categoryDaoJdbcImpl
     *
     * @param namedParameterJdbcTemplate jdbc template to inject
     * @param categoryMapper category mapper to inject
     * @param categoryDTOMapper category dto mapper to inject
     */
    @Autowired
    public CategoryDaoJdbcImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                               CategoryMapper categoryMapper,
                               CategoryDTOMapper categoryDTOMapper) {

        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.categoryMapper = categoryMapper;
        this.categoryDTOMapper = categoryDTOMapper;
    }

    /**
     * Returns all categories
     *
     * @return All categories as {@code Stream}
     */
    @Override
    public Stream<Category> findAll() {

        LOGGER.debug("findAll()");

        List<Category> categoryList = namedParameterJdbcTemplate.query(getAllCategoriesSql, categoryMapper);
        return categoryList.stream();
    }

    /**
     * Returns found category by defined id
     *
     * @param categoryId Id of element to find
     * @return {@code Optional} describing found category
     */
    @Override
    public Optional<Category> findById(Integer categoryId) {

        LOGGER.debug("findById({})", categoryId);

        MapSqlParameterSource namedParameters = new MapSqlParameterSource(CategoryMapper.CATEGORY_ID, categoryId);
        Category category = namedParameterJdbcTemplate.queryForObject(getCategoryByIdSql, namedParameters, categoryMapper);

        return Optional.ofNullable(category);
    }

    /**
     * Returns all categories Data Transfer Objects(DTO)
     *
     * @return Category DTOs as {@code Stream}
     */
    @Override
    public Stream<CategoryDTO> findAllCategoryDTOs() {

        LOGGER.debug("findAllCategoryDTOs()");

        List<CategoryDTO> categoryDTOList = namedParameterJdbcTemplate.query(getAllCategoryDTOsSql, categoryDTOMapper);
        return categoryDTOList.stream();
    }

    /**
     * Returns all sub categories
     *
     * @return Sub categories as {@code Stream}
     */
    @Override
    public Stream<Category> findAllSubCategories() {

        LOGGER.debug("findAllSubCategories()");

        List<Category> categories = namedParameterJdbcTemplate.query(getAllSubCategoriesSql, categoryMapper);
        return categories.stream();
    }

    /**
     * Returns category Data Transfer Object with defined id
     *
     * @param categoryId Category id to find
     * @return {@code Optional} describing found category DTOs
     */
    @Override
    public Optional<CategoryDTO> findCategoryDTOById(Integer categoryId) {

        LOGGER.debug("findCategoryDTOById()");

        MapSqlParameterSource namedParameters = new MapSqlParameterSource(CategoryDTOMapper.CATEGORY_DTO_ID, categoryId);
        CategoryDTO categoryDTO = namedParameterJdbcTemplate
                .queryForObject(getCategoryDTOByIdSql, namedParameters, categoryDTOMapper);

        return Optional.ofNullable(categoryDTO);
    }

    /**
     * Returns all sub categories Data Transfer Objects(DTO) that fits defined category id
     *
     * @param categoryId Category id to find by
     * @return Categories DTOs as {@code Stream}
     */
    @Override
    public Stream<CategoryDTO> findSubCategoryDTOsByCategoryId(Integer categoryId) {

        LOGGER.debug("findSubCategoryDTOsByCategoryId()");

        MapSqlParameterSource namedParameters = new MapSqlParameterSource(CategoryDTOMapper.CATEGORY_DTO_ID, categoryId);
        List<CategoryDTO> categoryDTOs =
                namedParameterJdbcTemplate
                        .query(getSubCategoryDTOsByCategoryIdSql,
                                namedParameters,
                                categoryDTOMapper);

        return categoryDTOs.stream();
    }

    /**
     * Save category to data source.
     *
     * @param category Category object to save in data source
     * @return Saved category with generated id
     */
    @Override
    public Optional<Category> add(Category category) {

        LOGGER.debug("add({})", category);

        MapSqlParameterSource namedParameters = getCategorySqlParametersSource(category);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(insertCategorySql, namedParameters, keyHolder);

        category.setCategoryId(keyHolder.getKey().intValue());

        return Optional.of(category);
    }

    /**
     * Update already existing category by new object
     *
     * @param category Object to replace older
     */
    @Override
    public void update(Category category) {

        LOGGER.debug("update({})", category);

        MapSqlParameterSource namedParameters = getCategorySqlParametersSource(category);
        namedParameters.addValue(CategoryMapper.CATEGORY_ID, category.getCategoryId());

        Optional.of(namedParameterJdbcTemplate.update(updateCategorySql, namedParameters))
                .filter(this::successfullyUpdate)
                .orElseThrow(() -> new DaoRuntimeException("Failed to update category in DB"))
        ;

    }

    /**
     * Delete category from data source
     *
     * @param categoryId Category id to delete
     * @throws DaoRuntimeException If selected category is a parent for some products
     */
    @Override
    public void delete(Integer categoryId) throws DaoRuntimeException {

        LOGGER.debug("delete({})", categoryId);

        MapSqlParameterSource namedParameters = new MapSqlParameterSource(CategoryMapper.CATEGORY_ID, categoryId);
        try {
            Optional.of(namedParameterJdbcTemplate.update(deleteCategorySql, namedParameters))
                    .filter(this::successfullyUpdate)
                    .orElseThrow(() -> new DaoRuntimeException("Failed to delete category"))
            ;
        } catch (DataIntegrityViolationException ex) {
            throw new DaoRuntimeException("There ara some products in category", ex);
        }


    }

    /**
     * Returns all possible categories that are parents for the given id.
     *
     * @param id id to find parent categories
     * @return Categories found as {@code Stream}
     */
    @Override
    public Stream<Category> findAllPossibleParentsForId(Integer id) {

        LOGGER.debug("findAllPossibleParentsFromId({})", id);

        MapSqlParameterSource namedParameters = new MapSqlParameterSource(CategoryMapper.CATEGORY_ID, id);
        List<Category> categories = namedParameterJdbcTemplate
                .query(getAllPossibleParentsForIdSql, namedParameters, categoryMapper);

        return categories.stream();
    }

    /**
     * Returns all possible categories that are parents.
     *
     * @return Categories found as {@code Stream}
     */
    @Override
    public Stream<Category> findAllPossibleParents() {

        LOGGER.debug("findAllPossibleParentsFromId()");

        List<Category> categories = namedParameterJdbcTemplate
                .query(getAllPossibleParentsSql, categoryMapper);

        return categories.stream();
    }

    private boolean successfullyUpdate(Integer numRowsUpdated) {
        return numRowsUpdated > 0;
    }

    private MapSqlParameterSource getCategorySqlParametersSource(Category category) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue(CategoryMapper.CATEGORY_NAME, category.getCategoryName());
        namedParameters.addValue(CategoryMapper.CATEGORY_PARENT_ID, category.getParentId());

        return namedParameters;
    }
}
