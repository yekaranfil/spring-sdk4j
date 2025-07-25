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
import java.time.OffsetDateTime;

public interface LessThanOrEqualJoinSpecs<J,T> {

    default Specification<T> lessThanOrEqualQuery(String joinedEntityName, String entityParam, Byte value){
        return ((root, criteriaQuery, criteriaBuilder) ->
        {
            Join<J, T> joinedEntity = root.join(joinedEntityName);
            return criteriaBuilder.lessThanOrEqualTo(joinedEntity.get(entityParam), value);
        });
    }

    default Specification<T> lessThanOrEqualQuery(String joinedEntityName, String entityParam, Integer value){
        return ((root, criteriaQuery, criteriaBuilder) ->
        {
            Join<J, T> joinedEntity = root.join(joinedEntityName);
            return criteriaBuilder.lessThanOrEqualTo(joinedEntity.get(entityParam), value);
        });
    }

    default Specification<T> lessThanOrEqualQuery(String joinedEntityName, String entityParam, Float value){
        return ((root, criteriaQuery, criteriaBuilder) ->
        {
            Join<J, T> joinedEntity = root.join(joinedEntityName);
            return criteriaBuilder.lessThanOrEqualTo(joinedEntity.get(entityParam), value);
        });
    }

    default Specification<T> lessThanOrEqualQuery(String joinedEntityName, String entityParam, Double value){
        return ((root, criteriaQuery, criteriaBuilder) ->
        {
            Join<J, T> joinedEntity = root.join(joinedEntityName);
            return criteriaBuilder.lessThanOrEqualTo(joinedEntity.get(entityParam), value);
        });
    }

    default Specification<T> lessThanOrEqualQuery(String joinedEntityName, String entityParam, Long value){
        return ((root, criteriaQuery, criteriaBuilder) ->
        {
            Join<J, T> joinedEntity = root.join(joinedEntityName);
            return criteriaBuilder.lessThanOrEqualTo(joinedEntity.get(entityParam), value);
        });
    }

    default Specification<T> lessThanOrEqualQuery(String joinedEntityName, String entityParam, OffsetDateTime value){
        return ((root, criteriaQuery, criteriaBuilder) ->
        {
            Join<J, T> joinedEntity = root.join(joinedEntityName);
            return criteriaBuilder.lessThanOrEqualTo(joinedEntity.get(entityParam), value);
        });
    }
}
