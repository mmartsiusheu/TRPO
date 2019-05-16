package com.epam.course.cp.model;

import java.util.Objects;

/**
 * Class describing {@code Category}
 *
 * @see com.epam.course.cp.dto.CategoryDTO
 * @author Maksim Martsiusheu
 */
public class Category {

    /**
     * Category id as {@code Integer}
     */
    private Integer categoryId;

    /**
     * Category name as {@code String}
     */
    private String categoryName;

    /**
     * Parent category id
     */
    private Integer parentId;

    /**
     * Returns category id
     * @return {@code Integer} as category id
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * Return category name
     *
     * @return {@code String} as category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Return parent category id
     *
     * @return {@code Integer} as parent category id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * Sets category id
     *
     * @param categoryId {@code Integer} as new category id
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Sets category name
     *
     * @param categoryName category name as {@code String}
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Sets parent id
     *
     * @param parentId parent id as {@code Integer}
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return categoryId.equals(category.categoryId) &&
                categoryName.equals(category.categoryName) &&
                parentId.equals(category.parentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryName, parentId);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
