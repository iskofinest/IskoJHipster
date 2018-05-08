package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;

import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the Product entity. This class is used in ProductResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /products?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private StringFilter reference;

    private StringFilter category;

    private StringFilter description;

    private StringFilter brand;

    private StringFilter model;

    private BigDecimalFilter quantity;

    private StringFilter unit;

    private LocalDateFilter product_date;

    private BigDecimalFilter originalPrice;

    private BigDecimalFilter sellingPrice;

    private StringFilter agent;

    private LongFilter suppliersId;

    public ProductCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getReference() {
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
    }

    public StringFilter getCategory() {
        return category;
    }

    public void setCategory(StringFilter category) {
        this.category = category;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getBrand() {
        return brand;
    }

    public void setBrand(StringFilter brand) {
        this.brand = brand;
    }

    public StringFilter getModel() {
        return model;
    }

    public void setModel(StringFilter model) {
        this.model = model;
    }

    public BigDecimalFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimalFilter quantity) {
        this.quantity = quantity;
    }

    public StringFilter getUnit() {
        return unit;
    }

    public void setUnit(StringFilter unit) {
        this.unit = unit;
    }

    public LocalDateFilter getProduct_date() {
        return product_date;
    }

    public void setProduct_date(LocalDateFilter product_date) {
        this.product_date = product_date;
    }

    public BigDecimalFilter getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimalFilter originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimalFilter getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimalFilter sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public StringFilter getAgent() {
        return agent;
    }

    public void setAgent(StringFilter agent) {
        this.agent = agent;
    }

    public LongFilter getSuppliersId() {
        return suppliersId;
    }

    public void setSuppliersId(LongFilter suppliersId) {
        this.suppliersId = suppliersId;
    }

    @Override
    public String toString() {
        return "ProductCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (reference != null ? "reference=" + reference + ", " : "") +
                (category != null ? "category=" + category + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (brand != null ? "brand=" + brand + ", " : "") +
                (model != null ? "model=" + model + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (unit != null ? "unit=" + unit + ", " : "") +
                (product_date != null ? "product_date=" + product_date + ", " : "") +
                (originalPrice != null ? "originalPrice=" + originalPrice + ", " : "") +
                (sellingPrice != null ? "sellingPrice=" + sellingPrice + ", " : "") +
                (agent != null ? "agent=" + agent + ", " : "") +
                (suppliersId != null ? "suppliersId=" + suppliersId + ", " : "") +
            "}";
    }

}
