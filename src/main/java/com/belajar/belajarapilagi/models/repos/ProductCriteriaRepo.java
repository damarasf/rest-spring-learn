package com.belajar.belajarapilagi.models.repos;

import com.belajar.belajarapilagi.models.entities.Product;
import com.belajar.belajarapilagi.models.entities.ProductPage;
import com.belajar.belajarapilagi.models.entities.ProductSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductCriteriaRepo {

    private final EntityManager entityManager;

    private final CriteriaBuilder criteriaBuilder;

    public ProductCriteriaRepo(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Product> findAll(
            ProductPage productPage,
            ProductSearchCriteria productSearchCriteria) {

        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> productRoot = criteriaQuery.from(Product.class);

        Predicate predicate = getPredicate(productSearchCriteria, productRoot);
        criteriaQuery.where(predicate);
        setOrder(productPage, criteriaQuery, productRoot);

        TypedQuery<Product> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(productPage.getPageNumber() * productPage.getPageSize());
        typedQuery.setMaxResults(productPage.getPageSize());

        Pageable pageable = getPageable(productPage);

        long productCount = getProductCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, productCount);
    }

    private long getProductCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Product> countRoot = countQuery.from(Product.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Pageable getPageable(ProductPage productPage) {
        Sort sort = Sort.by(productPage.getSortDirection(), productPage.getSortBy());
        return PageRequest.of(productPage.getPageNumber(), productPage.getPageSize(), sort);
    }

    private void setOrder(
            ProductPage productPage,
            CriteriaQuery<Product> criteriaQuery,
            Root<Product> productRoot) {
        if (productPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(productRoot.get(productPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(productRoot.get(productPage.getSortBy())));
        }
    }

    private Predicate getPredicate(
            ProductSearchCriteria productSearchCriteria,
            Root<Product> productRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(productSearchCriteria.getName())) {
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(productRoot.get("name")),
                            "%" + productSearchCriteria.getName().toLowerCase() + "%"));
        }
        if (Objects.nonNull(productSearchCriteria.getDescription())) {
            predicates.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(productRoot.get("description")),
                            "%" + productSearchCriteria.getDescription().toLowerCase() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
