package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 75)
    @Column(name = "name", length = 75, nullable = false)
    private String name;

    @NotNull
    @Size(max = 75)
    @Column(name = "reference", length = 75, nullable = false)
    private String reference;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @NotNull
    @Column(name = "quantity", precision=10, scale=2, nullable = false)
    private BigDecimal quantity;

    @NotNull
    @Column(name = "unit", nullable = false)
    private String unit;

    @NotNull
    @Column(name = "product_date", nullable = false)
    private LocalDate product_date;

    @NotNull
    @Column(name = "original_price", precision=10, scale=2, nullable = false)
    private BigDecimal originalPrice;

    @NotNull
    @Column(name = "selling_price", precision=10, scale=2, nullable = false)
    private BigDecimal sellingPrice;

    @Column(name = "agent")
    private String agent;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "product_suppliers",
               joinColumns = @JoinColumn(name="products_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="suppliers_id", referencedColumnName="id"))
    private Set<Supplier> suppliers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public Product reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getCategory() {
        return category;
    }

    public Product category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public Product brand(String brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public Product model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public Product quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public Product unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDate getProduct_date() {
        return product_date;
    }

    public Product product_date(LocalDate product_date) {
        this.product_date = product_date;
        return this;
    }

    public void setProduct_date(LocalDate product_date) {
        this.product_date = product_date;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public Product originalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
        return this;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public Product sellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
        return this;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getAgent() {
        return agent;
    }

    public Product agent(String agent) {
        this.agent = agent;
        return this;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public Set<Supplier> getSuppliers() {
        return suppliers;
    }

    public Product suppliers(Set<Supplier> suppliers) {
        this.suppliers = suppliers;
        return this;
    }

    public Product addSuppliers(Supplier supplier) {
        this.suppliers.add(supplier);
        supplier.getProducts().add(this);
        return this;
    }

    public Product removeSuppliers(Supplier supplier) {
        this.suppliers.remove(supplier);
        supplier.getProducts().remove(this);
        return this;
    }

    public void setSuppliers(Set<Supplier> suppliers) {
        this.suppliers = suppliers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if (product.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", reference='" + getReference() + "'" +
            ", category='" + getCategory() + "'" +
            ", description='" + getDescription() + "'" +
            ", brand='" + getBrand() + "'" +
            ", model='" + getModel() + "'" +
            ", quantity=" + getQuantity() +
            ", unit='" + getUnit() + "'" +
            ", product_date='" + getProduct_date() + "'" +
            ", originalPrice=" + getOriginalPrice() +
            ", sellingPrice=" + getSellingPrice() +
            ", agent='" + getAgent() + "'" +
            "}";
    }
}
