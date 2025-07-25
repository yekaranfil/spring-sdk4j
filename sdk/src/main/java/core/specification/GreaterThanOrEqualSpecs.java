/*
 *
 *  *
 *  *  *
 *  *  *  *
 *  *  *  *  *
 *  *  *  *  *  *  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *  *  *  *  *  *
 *  *  *  *  *  *  Copyright (C) 2025 Yerlem LLC  - All Rights Reserved.
 *  *  *  *  *  *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  *  *  *  *  *  Proprietary and confidential.
 *  *  *  *  *  *
 *  *  *  *  *  *  Written by Said Baysal <said_baysal@hotmail.com>, 4 2025
 *  *  *  *  *
 *  *  *  *
 *  *  *
 *  *
 *
 */

package core.specification;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;

public interface GreaterThanOrEqualSpecs<T> {

    default <Y extends Comparable<Y>> Specification<T> greaterThanOrEqual(String entityParam, Y value) {

        return (root, criteriaQuery, criteriaBuilder) -> {
            String[] pathList = entityParam.split("\\.");
            Path<Y> path = root.get(pathList[0]);
            for (int i = 1; i < pathList.length; i++) {
                path = path.get(pathList[i]);
            }
            return criteriaBuilder.greaterThanOrEqualTo(path, value);
        };
    }

    default <Y extends Comparable<Y>> Specification<T> greaterThanOrEqualJoin (String joinedEntityPath, JoinType joinType,
                                                                        String entityParam, Y value) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<T,?> joinedEntity = root.join(joinedEntityPath, joinType);
            String[] pathList = entityParam.split("\\.");
            Path<Y> path = joinedEntity.get(pathList[0]);
            for (int i = 1; i < pathList.length; i++) {
                path = path.get(pathList[i]);
            }
            return criteriaBuilder.greaterThanOrEqualTo(path, value);
        };
    }
}
