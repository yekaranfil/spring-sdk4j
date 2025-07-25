package core.executors;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import core.query.CoreQueryCustomizer;
import core.query.CoreQueryUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.lang.NonNull;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class CoreQuerydslExecutor<T> {

    private final EntityManager entityManager;
    private final PathBuilder<T> pathBuilder;
    private final Querydsl querydsl;
    private final JPAQueryFactory queryFactory;

    public CoreQuerydslExecutor(EntityManager entityManager, PathBuilder<T> pathBuilder, Querydsl querydsl, JPAQueryFactory queryFactory) {
        this.entityManager = entityManager;
        this.pathBuilder = pathBuilder;
        this.querydsl = querydsl;
        this.queryFactory = queryFactory;
    }


    ///  STANDARD METHODS

    @NonNull
    public Optional<T> findOne(Predicate predicate) {
        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder).where(predicate);
        return Optional.ofNullable(query.fetchOne());
    }

    @NonNull
    public Optional<T> findOne(Predicate predicate, CoreQueryCustomizer<T> customizer) {
        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder).where(predicate);
        customizer.accept(query);
        return Optional.ofNullable(query.fetchOne());
    }

    @NonNull
    public Iterable<T> findAll(Predicate predicate) {
        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder).where(predicate);
        return query.fetch();
    }

    @NonNull
    public Iterable<T> findAll(Predicate predicate, Sort sort) {
        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder).where(predicate);
        querydsl.applySorting(sort, query);
        return query.fetch();
    }

    @NonNull
    public Iterable<T> findAll(Predicate predicate, OrderSpecifier<?>... orderSpecifiers) {
        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder).where(predicate).orderBy(orderSpecifiers);
        return query.fetch();
    }

    @NonNull
    public Iterable<T> findAll(OrderSpecifier<?>... orderSpecifiers) {
        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder).orderBy(orderSpecifiers);
        return query.fetch();
    }

    @NonNull
    public Iterable<T> findAll(Predicate predicate,
                               CoreQueryCustomizer<T> customizer,
                               OrderSpecifier<?>... orderSpecifiers) {

        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder).where(predicate);
        customizer.accept(query);
        Optional.ofNullable(orderSpecifiers)
                .filter(arr -> arr.length > 0)
                .ifPresent(query::orderBy);

        return query.fetch();
    }

    @NonNull
    public Page<T> findAll(Predicate predicate, Pageable pageable) {
        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder)
                .where(predicate);
        querydsl.applyPagination(pageable, query);
        List<T> content = query.fetch();

        long total = Optional.ofNullable(
                queryFactory
                        .select(pathBuilder.count())
                        .from(pathBuilder)
                        .where(predicate)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }

    @NonNull
    public Page<T> findAll(Predicate predicate, Pageable pageable, OrderSpecifier<?>... orderSpecifiers) {
        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder)
                .where(predicate)
                .orderBy(orderSpecifiers);
        querydsl.applyPagination(pageable, query);
        List<T> content = query.fetch();

        long total = Optional.ofNullable(
                queryFactory
                        .select(pathBuilder.count())
                        .from(pathBuilder)
                        .where(predicate)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }

    @NonNull
    public Page<T> findAll(Predicate predicate,
                           Pageable pageable,
                           CoreQueryCustomizer<T> customizer,
                           OrderSpecifier<?>... orderSpecifiers) {

        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder)
                .where(predicate);
        customizer.accept(query);

        Optional.ofNullable(orderSpecifiers)
                .filter(arr -> arr.length > 0)
                .ifPresent(query::orderBy);

        querydsl.applyPagination(pageable, query);

        List<T> content = query.fetch();

        long total = Optional.ofNullable(
                queryFactory
                        .select(pathBuilder.count())
                        .from(pathBuilder)
                        .where(predicate)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }

    public long count(Predicate predicate) {
        return Optional.ofNullable(
                queryFactory
                        .select(pathBuilder.count())
                        .from(pathBuilder)
                        .where(predicate)
                        .fetchOne()
        ).orElse(0L);
    }

    public boolean exists(Predicate predicate) {
        return queryFactory
                .selectOne()
                .from(pathBuilder)
                .where(predicate)
                .fetchFirst() != null;
    }

    /// ENTITY GRAPH METHODS

    @NonNull
    public Optional<T> findOne(Predicate predicate, String entityGraphName) {
        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder).where(predicate);
        CoreQueryUtils.setEntityGraph(query, entityManager, entityGraphName);
        return Optional.ofNullable(query.fetchOne());
    }

    @NonNull
    public Optional<T> findOne(Predicate predicate, CoreQueryCustomizer<T> customizer, String entityGraphName) {
        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder).where(predicate);
        customizer.accept(query);
        CoreQueryUtils.setEntityGraph(query, entityManager, entityGraphName);
        return Optional.ofNullable(query.fetchOne());
    }

    @NonNull
    public Iterable<T> findAll(Predicate predicate, String entityGraphName) {
        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder).where(predicate);
        CoreQueryUtils.setEntityGraph(query, entityManager, entityGraphName);
        return query.fetch();
    }

    @NonNull
    public Iterable<T> findAll(Predicate predicate, Sort sort, String entityGraphName) {
        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder).where(predicate);
        CoreQueryUtils.setEntityGraph(query, entityManager, entityGraphName);
        querydsl.applySorting(sort, query);
        return query.fetch();
    }

    @NonNull
    public Iterable<T> findAll(Predicate predicate, String entityGraphName, OrderSpecifier<?>... orderSpecifiers) {
        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder).where(predicate).orderBy(orderSpecifiers);
        CoreQueryUtils.setEntityGraph(query, entityManager, entityGraphName);
        return query.fetch();
    }

    @NonNull
    public Iterable<T> findAll(String entityGraphName, OrderSpecifier<?>... orderSpecifiers) {
        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder).orderBy(orderSpecifiers);
        CoreQueryUtils.setEntityGraph(query, entityManager, entityGraphName);
        return query.fetch();
    }

    @NonNull
    public Iterable<T> findAll(Predicate predicate,
                               CoreQueryCustomizer<T> customizer,
                               String entityGraphName, OrderSpecifier<?>... orderSpecifiers) {

        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder).where(predicate);
        customizer.accept(query);
        Optional.ofNullable(orderSpecifiers)
                .filter(arr -> arr.length > 0)
                .ifPresent(query::orderBy);

        CoreQueryUtils.setEntityGraph(query, entityManager, entityGraphName);
        return query.fetch();
    }

    @NonNull
    public Iterable<T> findAll(Predicate predicate,
                               CoreQueryCustomizer<T> customizer, String entityGraphName,
                               Boolean isDistinct, OrderSpecifier<?>... orderSpecifiers) {

        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder).where(predicate);
        customizer.accept(query);
        if (isDistinct.equals(Boolean.TRUE)) {
            query.distinct();
        }
        Optional.ofNullable(orderSpecifiers)
                .filter(arr -> arr.length > 0)
                .ifPresent(query::orderBy);

        CoreQueryUtils.setEntityGraph(query, entityManager, entityGraphName);
        return query.fetch();
    }

    @NonNull
    public Page<T> findAll(Predicate predicate, Pageable pageable, String entityGraphName) {
        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder)
                .where(predicate);
        CoreQueryUtils.setEntityGraph(query, entityManager, entityGraphName);
        querydsl.applyPagination(pageable, query);
        List<T> content = query.fetch();

        long total = Optional.ofNullable(
                queryFactory
                        .select(pathBuilder.count())
                        .from(pathBuilder)
                        .where(predicate)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }

    @NonNull
    public Page<T> findAll(Predicate predicate, Pageable pageable, String entityGraphName, OrderSpecifier<?>... orderSpecifiers) {
        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder)
                .where(predicate)
                .orderBy(orderSpecifiers);
        CoreQueryUtils.setEntityGraph(query, entityManager, entityGraphName);

        querydsl.applyPagination(pageable, query);

        Optional.ofNullable(orderSpecifiers)
                .filter(arr -> arr.length > 0)
                .ifPresent(query::orderBy);

        List<T> content = query.fetch();

        long total = Optional.ofNullable(
                queryFactory
                        .select(pathBuilder.count())
                        .from(pathBuilder)
                        .where(predicate)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }

    @NonNull
    public Page<T> findAll(Predicate predicate,
                           Pageable pageable,
                           CoreQueryCustomizer<T> customizer,
                           String entityGraphName,
                           OrderSpecifier<?>... orderSpecifiers) {

        JPAQuery<T> query = queryFactory.selectFrom(pathBuilder)
                .where(predicate);

        customizer.accept(query);

        Optional.ofNullable(orderSpecifiers)
                .filter(arr -> arr.length > 0)
                .ifPresent(query::orderBy);

        CoreQueryUtils.setEntityGraph(query, entityManager, entityGraphName);

        querydsl.applyPagination(pageable, query);

        Optional.ofNullable(orderSpecifiers)
                .filter(arr -> arr.length > 0)
                .ifPresent(query::orderBy);

        List<T> content = query.fetch();

        long total = Optional.ofNullable(
                queryFactory
                        .select(pathBuilder.count())
                        .from(pathBuilder)
                        .where(predicate)
                        .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(content, pageable, total);
    }

    @NonNull
    public <ID> Page<T> findAllInTwoSteps(Predicate predicate, Pageable pageable, Path<ID> idPath, String entityGraphName) {

        JPAQuery<ID> idQuery = queryFactory
                .select(idPath)
                .from(pathBuilder)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<ID> ids = idQuery.fetch();

        if (ids.isEmpty()) return Page.empty(pageable);

        @SuppressWarnings("unchecked")
        JPAQuery<T> fetchQuery = (JPAQuery<T>) queryFactory
                .selectFrom(pathBuilder)
                .where(((SimpleExpression<ID>) idPath).in(ids));

        CoreQueryUtils.setEntityGraph(fetchQuery, entityManager, entityGraphName);

        List<T> content = fetchQuery.fetch();
        long total = count(predicate);
        return new PageImpl<>(content, pageable, total);
    }

    @NonNull
    public <ID> Page<T> findAllInTwoSteps(Predicate predicate, Pageable pageable, Path<ID> idPath, String entityGraphName, OrderSpecifier<?>... orderSpecifiers) {

        JPAQuery<ID> idQuery = (JPAQuery<ID>) queryFactory
                .select(idPath)
                .from(pathBuilder)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        Optional.ofNullable(orderSpecifiers)
                .filter(arr -> arr.length > 0)
                .ifPresent(idQuery::orderBy);

        List<ID> ids = idQuery.fetch();

        if (ids.isEmpty()) {
            return Page.empty(pageable);
        }


        @SuppressWarnings("unchecked")
        JPAQuery<T> fetchQuery = (JPAQuery<T>) queryFactory
                .selectFrom(pathBuilder)
                .where(((SimpleExpression<ID>) idPath).in(ids));

        Optional.ofNullable(orderSpecifiers)
                .filter(arr -> arr.length > 0)
                .ifPresent(fetchQuery::orderBy);

        CoreQueryUtils.setEntityGraph(fetchQuery, entityManager, entityGraphName);

        List<T> content = fetchQuery.fetch();
        long total = count(predicate);
        return new PageImpl<>(content, pageable, total);
    }

    @NonNull
    public <ID> Page<T> findAllInTwoSteps(Predicate predicate,
                                          Pageable pageable, Path<ID> idPath,
                                          CoreQueryCustomizer<ID> idCustomizer,
                                          CoreQueryCustomizer<T> fetchCustomizer,
                                          String entityGraphName,
                                          OrderSpecifier<?>... orderSpecifiers) {

        JPAQuery<ID> idQuery = (JPAQuery<ID>) queryFactory
                .select(idPath)
                .from(pathBuilder)
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        Optional.ofNullable(idCustomizer)
                .ifPresent(c -> c.accept(idQuery));

        Optional.ofNullable(orderSpecifiers)
                .filter(arr -> arr.length > 0)
                .ifPresent(idQuery::orderBy);

        List<ID> ids = idQuery.fetch();

        if (ids.isEmpty()) {
            return Page.empty(pageable);
        }


        @SuppressWarnings("unchecked")
        JPAQuery<T> fetchQuery = (JPAQuery<T>) queryFactory
                .selectFrom(pathBuilder)
                .where(((SimpleExpression<ID>) idPath).in(ids));

        Optional.ofNullable(fetchCustomizer)
                .ifPresent(c -> c.accept(fetchQuery));

        Optional.ofNullable(orderSpecifiers)
                .filter(arr -> arr.length > 0)
                .ifPresent(fetchQuery::orderBy);

        CoreQueryUtils.setEntityGraph(fetchQuery, entityManager, entityGraphName);

        List<T> content = fetchQuery.fetch();
        long total = count(predicate);
        return new PageImpl<>(content, pageable, total);
    }
}
