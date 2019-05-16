package com.epam.course.cp.dto;

import java.util.Objects;

/**
 * Class representing {@code category Data Transfer Object}
 *
 * @see com.epam.course.cp.model.Category
 * @author Maksim Martsiusheu
 */
public class CategoryDTO {

    /**
     * {@code category DTO} id
     */
    private Integer categoryId;

    /**
     * String representing {@code category} name
     */
    private String categoryName;

    /**
     * Parent {@code category} id
     */
    private Integer parentId;

    /**
     * Amount of products in {@code category}
     */
    private Integer productsAmount;

    /**
     * Gets a category id
     *
     * @return {@code Integer} as category id
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * Returns amount of products in category
     *
     * @return {@code Integer} as products amount
     */
    public Integer getProductsAmount() {
        return productsAmount;
    }

    /**
     * Returns category name
     *
     * @return {@code String} representing category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Returns parent category id
     *
     * @return {@code Integer} as parent category id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * Sets category id
     *
     * @param categoryId new value of category id
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Sets product amount in category
     * @param productsAmount new products amount in category
     */
    public void setProductsAmount(Integer productsAmount) {
        this.productsAmount = productsAmount;
    }

    /**
     * Sets new category name
     *
     * @param categoryName {@code String} as new category name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Sets id of parent category
     *
     * @param parentId {@code Integer} as new value of parent category id
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDTO that = (CategoryDTO) o;
        return categoryId.equals(that.categoryId) &&
                categoryName.equals(that.categoryName) &&
                parentId.equals(that.parentId) &&
                productsAmount.equals(that.productsAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryName, parentId, productsAmount);
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", parentId=" + parentId +
                ", productsAmount=" + productsAmount +
                '}';
    }
}
