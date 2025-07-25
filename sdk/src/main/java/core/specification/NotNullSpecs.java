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
import java.util.Collection;

public interface NotNullSpecs<T>{

    default <C extends Collection<?>> Specification<T> isNotNull(String entityParam) {

        return (root, criteriaQuery, criteriaBuilder) -> {
            String[] pathList = entityParam.split("\\.");
            Path<C> path = root.get(pathList[0]);
            for (int i = 1; i < pathList.length; i++) {
                path = path.get(pathList[i]);
            }
            return criteriaBuilder.isNotNull(path);
        };
    }

    default <C extends Collection<?>> Specification<T> isNotNullJoin (String joinedEntityPath, JoinType joinType, String entityParam) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Join<T, ?> joinedEntity = root.join(joinedEntityPath, joinType);
            String[] pathList = entityParam.split("\\.");
            Path<C> path = joinedEntity.get(pathList[0]);
            for (int i = 1; i < pathList.length; i++) {
                path = path.get(pathList[i]);
            }
            return criteriaBuilder.isNotNull(path);
        };
    }
}
