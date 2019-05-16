package com.epam.course.cp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Class representing {@code product Data Transfer Object}
 *
 * @see com.epam.course.cp.model.Product
 * @author Maksim Martsiusheu
 */
public class ProductDTO {

    /**
     * {@code product DTO} id
     */
    private Integer productId;

    /**
     * String representing {@code category} name
     */
    private String categoryName;

    /**
     * String representing {@code category} name
     */
    private String subCategoryName;

    /**
     * String representing {@code product} name
     */
    private String productName;

    /**
     * Amount of products in {@code category}
     */
    private Integer productAmount;

    /**
     * Date when product have been added
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateAdded;

    /**
     * {@code Category id} to which belongs
     */
    private Integer categoryId;

    /**
     * Returns product DTOs id
     *
     * @return {@code Integer} as product id
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * Retirns {@code category} name
     *
     * @return {@code String} representing category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Returns sub {@code category} name
     *
     * @return {@code String} as sub category name
     */
    public String getSubCategoryName() {
        return subCategoryName;
    }

    /**
     * Retirns {@code product} name
     *
     * @return {@code String} as product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Returns {@code product} amount
     *
     * @return {@code Integer} as product amount
     */
    public Integer getProductAmount() {
        return productAmount;
    }

    /**
     * Returns date when was added
     *
     * @return {@code LocalDate} as date when added
     */
    public LocalDate getDateAdded() {
        return dateAdded;
    }

    /**
     * Returns {@code category} id
     *
     * @return {@code Integer} as category id
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * Sets product id
     *
     * @param productId product id {@code Integer value}
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * Sets category name
     *
     * @param categoryName category name {@code String}
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Sets sub category name
     *
     * @param subCategoryName sub category name as {@code String}
     */
    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    /**
     * Sets product name
     *
     * @param productName product name as {@code String}
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Sets product amount
     *
     * @param productAmount product amount as {@code Integer}
     */
    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }

    /**
     * Sets date when was added
     *
     * @param dateAdded date added as {@code LocalDate}
     */
    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * Sets category id
     *
     * @param categoryId category id as {@code Integer}
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return productId.equals(that.productId) &&
                categoryName.equals(that.categoryName) &&
                subCategoryName.equals(that.subCategoryName) &&
                productName.equals(that.productName) &&
                productAmount.equals(that.productAmount) &&
                dateAdded.equals(that.dateAdded) &&
                categoryId.equals(that.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, categoryName, subCategoryName, productName, productAmount, dateAdded, categoryId);
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "productId=" + productId +
                ", categoryName='" + categoryName + '\'' +
                ", subCategoryName='" + subCategoryName + '\'' +
                ", productName='" + productName + '\'' +
                ", productAmount=" + productAmount +
                ", dateAdded=" + dateAdded +
                ", categoryId=" + categoryId +
                '}';
    }
}
