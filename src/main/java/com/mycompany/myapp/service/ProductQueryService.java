package com.mycompany.myapp.service;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.ProductRepository;
import com.mycompany.myapp.service.dto.ProductCriteria;


/**
 * Service for executing complex queries for Product entities in the database.
 * The main input is a {@link ProductCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Product} or a {@link Page} of {@link Product} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductQueryService extends QueryService<Product> {

    private final Logger log = LoggerFactory.getLogger(ProductQueryService.class);


    private final ProductRepository productRepository;

    public ProductQueryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Return a {@link List} of {@link Product} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Product> findByCriteria(ProductCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Product> specification = createSpecification(criteria);
        return productRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Product} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Product> findByCriteria(ProductCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Product> specification = createSpecification(criteria);
        return productRepository.findAll(specification, page);
    }

    /**
     * Function to convert ProductCriteria to a {@link Specifications}
     */
    private Specifications<Product> createSpecification(ProductCriteria criteria) {
        Specifications<Product> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Product_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Product_.name));
            }
            if (criteria.getReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReference(), Product_.reference));
            }
            if (criteria.getCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategory(), Product_.category));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Product_.description));
            }
            if (criteria.getBrand() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBrand(), Product_.brand));
            }
            if (criteria.getModel() != null) {
                specification = specification.and(buildStringSpecification(criteria.getModel(), Product_.model));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), Product_.quantity));
            }
            if (criteria.getUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnit(), Product_.unit));
            }
            if (criteria.getProduct_date() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProduct_date(), Product_.product_date));
            }
            if (criteria.getOriginalPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOriginalPrice(), Product_.originalPrice));
            }
            if (criteria.getSellingPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSellingPrice(), Product_.sellingPrice));
            }
            if (criteria.getAgent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAgent(), Product_.agent));
            }
            if (criteria.getSuppliersId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getSuppliersId(), Product_.suppliers, Supplier_.id));
            }
        }
        return specification;
    }

}
