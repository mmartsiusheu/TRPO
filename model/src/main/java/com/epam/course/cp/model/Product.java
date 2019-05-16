package com.epam.course.cp.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Class describing product from a {@code Category}
 *
 * @see com.epam.course.cp.dto.ProductDTO
 * @author Maksim Martsiusheu
 */
public class Product {

    /**
     * Product id
     */
    private Integer productId;

    /**
     * String representing {@code product} name
     */
    private String productName;

    /**
     * Amount of products
     */
    private Integer productAmount;

    /**
     * {@code LocalDate} as date when product was added
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateAdded;

    /**
     * Category id in which product exist
     */
    private Integer categoryId;

    /**
     * Returns product id
     *
     * @return {@code Integer} as product id
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * Returns {@code product} name
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
     * Returns {@code category} id in which product is located
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
        Product product = (Product) o;
        return productId.equals(product.productId) &&
                productName.equals(product.productName) &&
                productAmount.equals(product.productAmount) &&
                dateAdded.equals(product.dateAdded) &&
                categoryId.equals(product.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, productAmount, dateAdded, categoryId);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productAmount=" + productAmount +
                ", dateAdded=" + dateAdded +
                ", categoryId=" + categoryId +
                '}';
    }
}
