package com.mycompany.myapp.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.Supplier;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.SupplierRepository;
import com.mycompany.myapp.service.dto.SupplierCriteria;


/**
 * Service for executing complex queries for Supplier entities in the database.
 * The main input is a {@link SupplierCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Supplier} or a {@link Page} of {@link Supplier} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplierQueryService extends QueryService<Supplier> {

    private final Logger log = LoggerFactory.getLogger(SupplierQueryService.class);


    private final SupplierRepository supplierRepository;

    public SupplierQueryService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    /**
     * Return a {@link List} of {@link Supplier} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Supplier> findByCriteria(SupplierCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Supplier> specification = createSpecification(criteria);
        return supplierRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Supplier} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Supplier> findByCriteria(SupplierCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Supplier> specification = createSpecification(criteria);
        return supplierRepository.findAll(specification, page);
    }

    /**
     * Function to convert SupplierCriteria to a {@link Specifications}
     */
    private Specifications<Supplier> createSpecification(SupplierCriteria criteria) {
        Specifications<Supplier> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Supplier_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Supplier_.name));
            }
            if (criteria.getContactPerson() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactPerson(), Supplier_.contactPerson));
            }
            if (criteria.getContactDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactDetails(), Supplier_.contactDetails));
            }
            if (criteria.getProductsId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getProductsId(), Supplier_.products, Product_.id));
            }
        }
        return specification;
    }

}
