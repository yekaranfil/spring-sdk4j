package core.executors;


import core.query.CoreQueryUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.lang.Nullable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class CoreSpecificationExecutor<T> {

    private final EntityManager entityManager;
    private final Class<T> domainClass;

    public CoreSpecificationExecutor(EntityManager entityManager, Class<T> domainClass) {
        this.entityManager = entityManager;
        this.domainClass = domainClass;
    }

    private TypedQuery<T> getQuery(@Nullable Specification<T> spec, @Nullable Sort sort, String entityGraphName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(domainClass);
        Root<T> root = query.from(domainClass);

        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, query, cb);
            if (predicate != null) {
                query.where(predicate);
            }
        }

        if (sort != null) {
            query.orderBy(QueryUtils.toOrders(sort, root, cb));
        }

        TypedQuery<T> typedQuery = entityManager.createQuery(query);
        CoreQueryUtils.setEntityGraph(typedQuery, entityManager, entityGraphName);
        return typedQuery;
    }

    public Optional<T> findOne(@Nullable Specification<T> spec, String entityGraphName) {
        List<T> results = getQuery(spec, null, entityGraphName)
                .setMaxResults(1)
                .getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public List<T> findAll(@Nullable Specification<T> spec, String entityGraphName) {
        return getQuery(spec, null, entityGraphName).getResultList();
    }

    public List<T> findAll(@Nullable Specification<T> spec, Sort sort, String entityGraphName) {
        return getQuery(spec, sort, entityGraphName).getResultList();
    }

    public Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable, String entityGraphName) {
        TypedQuery<T> query = getQuery(spec, pageable.getSort(), entityGraphName);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(domainClass);
        countQuery.select(cb.count(countRoot));

        if (spec != null) {
            Predicate predicate = spec.toPredicate(countRoot, countQuery, cb);
            if (predicate != null) {
                countQuery.where(predicate);
            }
        }

        long total = entityManager.createQuery(countQuery).getSingleResult();
        List<T> content = query.getResultList();
        return new PageImpl<>(content, pageable, total);
    }
}
