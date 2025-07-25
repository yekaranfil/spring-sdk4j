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
import core.query.CoreQueryCustomizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CoreRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T>, QuerydslPredicateExecutor<T> {

    /// SPECIFICATION ENTITY GRAPH METHODS

    Optional<T> findOne(@Nullable Specification<T> spec, String entityGraphName);

    List<T> findAll(@Nullable Specification<T> spec, String entityGraphName);

    List<T> findAll(@Nullable Specification<T> spec, Sort sort, String entityGraphName);

    Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable, String entityGraphName);

    /// QUERYDSL EXTRA METHODS

    Optional<T> findOne(Predicate predicate, CoreQueryCustomizer<T> customizer);

    Page<T> findAll(Predicate predicate, Pageable pageable, OrderSpecifier<?>... orderSpecifiers);

    Page<T> findAll(Predicate predicate, Pageable pageable, CoreQueryCustomizer<T> customizer, OrderSpecifier<?>... orderSpecifiers);

    Iterable<T> findAll(Predicate predicate, CoreQueryCustomizer<T> customizer, OrderSpecifier<?>... orderSpecifiers);

    /// QUERYDSL ENTITY GRAPH METHODS

    Optional<T> findOne(Predicate predicate, String entityGraphName);

    Optional<T> findOne(Predicate predicate, CoreQueryCustomizer<T> customizer, String entityGraphName);

    Iterable<T> findAll(Predicate predicate, String entityGraphName);

    Iterable<T> findAll(Predicate predicate, Sort sort, String entityGraphName);

    Iterable<T> findAll(Predicate predicate, String entityGraphName, OrderSpecifier<?>... orderSpecifiers);

    Iterable<T> findAll(String entityGraphName, OrderSpecifier<?>... orderSpecifiers);

    Iterable<T> findAll(Predicate predicate, CoreQueryCustomizer<T> customizer, String entityGraphName, OrderSpecifier<?>... orderSpecifiers);

    Iterable<T> findAll(Predicate predicate, CoreQueryCustomizer<T> customizer, String entityGraphName, Boolean isDistinct, OrderSpecifier<?>... orderSpecifiers);

    Page<T> findAll(Predicate predicate, Pageable pageable, String entityGraphName);

    Page<T> findAll(Predicate predicate, Pageable pageable, String entityGraphName, OrderSpecifier<?>... orderSpecifiers);

    Page<T> findAll(Predicate predicate, Pageable pageable, CoreQueryCustomizer<T> customizer, String entityGraphName, OrderSpecifier<?>... orderSpecifiers);

    /// TWO STEPS FETCH METHODS

    Page<T> findAllInTwoSteps(Predicate predicate, Pageable pageable, Path<ID> idPath, String entityGraphName);

    Page<T> findAllInTwoSteps(Predicate predicate, Pageable pageable, Path<ID> idPath, String entityGraphName, OrderSpecifier<?>... orderSpecifiers);

    Page<T> findAllInTwoSteps(Predicate predicate, Pageable pageable, Path<ID> idPath,
                              CoreQueryCustomizer<ID> idCustomizer, CoreQueryCustomizer<T> fetchCustomizer,
                              String entityGraphName, OrderSpecifier<?>... orderSpecifiers);

}
