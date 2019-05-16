package com.epam.course.cp.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

/**
 * {@code Filter} class wraps date interval filter and category
 * id which are used to search for products
 *
 * @see LocalDate
 * @author Maksim Martsiusheu
 */
public class Filter {

    /**
     * Category id to find product by
     */
    private Integer categoryId;

    /**
     * Starting date of two dates for product search
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateBegin;

    /**
     * Ending date of two dates for products search
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateEnd;

    /**
     * Return the category id
     *
     * @return {@code Integer} representing category id
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * Returns date interval begin date
     *
     * @return {@code LocalDate} representing date interval beginning
     */
    public LocalDate getDateBegin() {
        return dateBegin;
    }

    /**
     * Returns date interval end date
     *
     * @return {@code LocalDate} representing date interval end
     */
    public LocalDate getDateEnd() {
        return dateEnd;
    }

    /**
     * Sets category id to find by
     *
     * @param categoryId {@code Integer} as category id
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Sets date interval beginning
     *
     * @param dateBegin {@code LocalDate} as beginning date
     */
    public void setDateBegin(LocalDate dateBegin) {
        this.dateBegin = dateBegin;
    }

    /**
     * Sets date interval ending
     *
     * @param dateEnd {@code LocalDate} as ending date
     */
    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filter filter = (Filter) o;
        return categoryId.equals(filter.categoryId) &&
                dateBegin.equals(filter.dateBegin) &&
                dateEnd.equals(filter.dateEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, dateBegin, dateEnd);
    }

    @Override
    public String toString() {
        return "Filter{" +
                "categoryId=" + categoryId +
                ", dateBegin=" + dateBegin +
                ", dateEnd=" + dateEnd +
                '}';
    }
}
