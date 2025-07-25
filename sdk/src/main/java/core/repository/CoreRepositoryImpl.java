/*
 *
 *  *
 *  *  *
 *  *  *  *
 *  *  *  *  *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *  *  *  *  *
 *  *  *  *  *  Copyright (C) 2025 Yerlem LLC  - All Rights Reserved.
 *  *  *  *  *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  *  *  *  *  Proprietary and confidential.
 *  *  *  *  *
 *  *  *  *  *  Written by Said Baysal <said_baysal@hotmail.com>, April 2025
 *  *  *  *
 *  *  *
 *  *
 *
 */

package core.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import core.executors.CoreQuerydslExecutor;
import core.executors.CoreSpecificationExecutor;
import core.query.CoreQueryCustomizer;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


@NoRepositoryBean
public class CoreRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CoreRepository<T, ID> {

    private final CoreSpecificationExecutor<T> yerlemSpecificationExecutor;
    private final CoreQuerydslExecutor<T> yerlemQuerydslExecutor;

    public CoreRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {

        super(entityInformation, entityManager);
        Class<T> domainClass = entityInformation.getJavaType();
        EntityPathResolver resolver = SimpleEntityPathResolver.INSTANCE;
        PathBuilder<T> pathBuilder = new PathBuilder<>(domainClass, resolver.createPath(domainClass).getMetadata().getName());
        Querydsl querydsl = new Querydsl(entityManager, pathBuilder);
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        this.yerlemSpecificationExecutor = new CoreSpecificationExecutor<T>(entityManager, domainClass);
        this.yerlemQuerydslExecutor = new CoreQuerydslExecutor<T>(entityManager, pathBuilder, querydsl, queryFactory);
    }


    /// SPECIFICATIONS ENTITY GRAPH METHODS

    @Override
    public Optional<T> findOne(@Nullable Specification<T> spec, @Nullable String entityGraphName) {
        return yerlemSpecificationExecutor.findOne(spec, entityGraphName);
    }

    @Override
    public List<T> findAll(@Nullable Specification<T> spec, @Nullable String entityGraphName) {
        return yerlemSpecificationExecutor.findAll(spec, entityGraphName);
    }

    @Override
    public List<T> findAll(@Nullable Specification<T> spec, Sort sort, @Nullable String entityGraphName) {
        return yerlemSpecificationExecutor.findAll(spec, sort, entityGraphName);
    }

    @Override
    public Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable, @Nullable String entityGraphName) {
        return yerlemSpecificationExecutor.findAll(spec, pageable, entityGraphName);
    }


    /// QUERYDSL STANDARD METHODS

    @Override
    @NonNull
    public Optional<T> findOne(@Nullable Predicate predicate) {
        return yerlemQuerydslExecutor.findOne(predicate);
    }

    @Override
    public Optional<T> findOne(@Nullable Predicate predicate, CoreQueryCustomizer<T> customizer) {
        return yerlemQuerydslExecutor.findOne(predicate, customizer);
    }

    @Override
    @NonNull
    public Iterable<T> findAll(@Nullable Predicate predicate) {
        return yerlemQuerydslExecutor.findAll(predicate);
    }

    @Override
    @NonNull
    public Iterable<T> findAll(@Nullable Predicate predicate, @NonNull Sort sort) {
        return yerlemQuerydslExecutor.findAll(predicate, sort);
    }

    @Override
    @NonNull
    public Iterable<T> findAll(@Nullable Predicate predicate, @NonNull OrderSpecifier<?>... orderSpecifiers) {
        return yerlemQuerydslExecutor.findAll(predicate, orderSpecifiers);
    }

    @Override
    @NonNull
    public Iterable<T> findAll(@NonNull OrderSpecifier<?>... orderSpecifiers) {
        return yerlemQuerydslExecutor.findAll(orderSpecifiers);
    }

    @Override
    @NonNull
    public Page<T> findAll(@Nullable Predicate predicate, @NonNull Pageable pageable) {
        return yerlemQuerydslExecutor.findAll(predicate, pageable);
    }

    @Override
    @NonNull
    public Page<T> findAll(@Nullable Predicate predicate, @NonNull Pageable pageable, @NonNull OrderSpecifier<?>... orderSpecifiers) {
        return yerlemQuerydslExecutor.findAll(predicate, pageable, orderSpecifiers);
    }

    @Override
    @NonNull
    public Page<T> findAll(@Nullable Predicate predicate, @NonNull Pageable pageable, @NonNull CoreQueryCustomizer<T> customizer, @NonNull OrderSpecifier<?>... orderSpecifiers) {
        return yerlemQuerydslExecutor.findAll(predicate, pageable, customizer, orderSpecifiers);
    }

    @Override
    public Iterable<T> findAll(@Nullable Predicate predicate, CoreQueryCustomizer<T> customizer, OrderSpecifier<?>... orderSpecifiers) {
        return yerlemQuerydslExecutor.findAll(predicate, customizer, orderSpecifiers);
    }

    @Override
    public long count(@Nullable Predicate predicate) {
        return yerlemQuerydslExecutor.count(predicate);
    }

    @Override
    public boolean exists(@Nullable Predicate predicate) {
        return yerlemQuerydslExecutor.exists(predicate);
    }

    @Override
    public <S extends T, R> R findBy(Predicate predicate, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    /// QUERYDSL ENTITY GRAPH METHODS

    @Override
    @NonNull
    public Optional<T> findOne(@Nullable Predicate predicate, String entityGraphName) {
        return yerlemQuerydslExecutor.findOne(predicate, entityGraphName);
    }

    @Override
    public Optional<T> findOne(@Nullable Predicate predicate, CoreQueryCustomizer<T> customizer, String entityGraphName) {
        return yerlemQuerydslExecutor.findOne(predicate, customizer, entityGraphName);
    }

    @Override
    @NonNull
    public Iterable<T> findAll(@Nullable Predicate predicate, String entityGraphName) {
        return yerlemQuerydslExecutor.findAll(predicate, entityGraphName);
    }

    @Override
    @NonNull
    public Iterable<T> findAll(@Nullable Predicate predicate, @NonNull Sort sort, String entityGraphName) {
        return yerlemQuerydslExecutor.findAll(predicate, sort, entityGraphName);
    }

    @Override
    @NonNull
    public Iterable<T> findAll(@Nullable Predicate predicate, String entityGraphName, @NonNull OrderSpecifier<?>... orderSpecifiers) {
        return yerlemQuerydslExecutor.findAll(predicate, entityGraphName, orderSpecifiers);
    }

    @Override
    @NonNull
    public Iterable<T> findAll(String entityGraphName, @NonNull OrderSpecifier<?>... orderSpecifiers) {
        return yerlemQuerydslExecutor.findAll(entityGraphName, orderSpecifiers);
    }

    @Override
    public Iterable<T> findAll(@Nullable Predicate predicate, CoreQueryCustomizer<T> customizer, String entityGraphName, OrderSpecifier<?>... orderSpecifiers) {
        return yerlemQuerydslExecutor.findAll(predicate, customizer, entityGraphName, orderSpecifiers);
    }

    @Override
    public Iterable<T> findAll(Predicate predicate, CoreQueryCustomizer<T> customizer, String entityGraphName, Boolean isDistinct, OrderSpecifier<?>... orderSpecifiers) {
        return yerlemQuerydslExecutor.findAll(predicate, customizer, entityGraphName, isDistinct, orderSpecifiers);
    }

    @Override
    @NonNull
    public Page<T> findAll(@Nullable Predicate predicate, @NonNull Pageable pageable, String entityGraphName) {
        return yerlemQuerydslExecutor.findAll(predicate, pageable, entityGraphName);
    }

    @Override
    @NonNull
    public Page<T> findAll(@Nullable Predicate predicate, @NonNull Pageable pageable, String entityGraphName, @NonNull OrderSpecifier<?>... orderSpecifiers) {
        return yerlemQuerydslExecutor.findAll(predicate, pageable, entityGraphName, orderSpecifiers);
    }

    @Override
    @NonNull
    public Page<T> findAll(@Nullable Predicate predicate, @NonNull Pageable pageable, @NonNull CoreQueryCustomizer<T> customizer,
                           String entityGraphName, @NonNull OrderSpecifier<?>... orderSpecifiers) {
        return yerlemQuerydslExecutor.findAll(predicate, pageable, customizer, entityGraphName, orderSpecifiers);
    }

    /// TWO STEPS FETCH METHODS

    @Override
    public Page<T> findAllInTwoSteps(Predicate predicate, Pageable pageable, Path<ID> path, String entityGraphName) {
        return yerlemQuerydslExecutor.findAllInTwoSteps(predicate, pageable, path, entityGraphName);
    }

    @Override
    public Page<T> findAllInTwoSteps(Predicate predicate, Pageable pageable, Path<ID> path, String entityGraphName, OrderSpecifier<?>... orderSpecifiers) {
        return yerlemQuerydslExecutor.findAllInTwoSteps(predicate, pageable, path, entityGraphName, orderSpecifiers);
    }

    @Override
    public Page<T> findAllInTwoSteps(Predicate predicate, Pageable pageable, Path<ID> path,
                                     CoreQueryCustomizer<ID> idCustomizer, CoreQueryCustomizer<T> fetchCustomizer,
                                     String entityGraphName, OrderSpecifier<?>... orderSpecifiers) {
        return yerlemQuerydslExecutor.findAllInTwoSteps(predicate, pageable, path, idCustomizer, fetchCustomizer, entityGraphName, orderSpecifiers);
    }


}
