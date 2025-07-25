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

public interface NotLikeSpecs<T> {

    default Specification<T> notLike(String entityParam, String value, Boolean isLowerCase){
        return ((root, criteriaQuery, criteriaBuilder) ->
        {
            String likeStr = isLowerCase ? "%"+ value.toLowerCase() + "%" : "%" + value + "%";
            String[] pathList = entityParam.split("\\.");
            Path<String> path = root.get(pathList[0]);
            for (int i = 1; i < pathList.length; i++) {
                path = path.get(pathList[i]);
            }
            return isLowerCase ? criteriaBuilder.notLike(criteriaBuilder.lower(path), likeStr):
                    criteriaBuilder.notLike(path, likeStr);
        });
    }

    default Specification<T> notLikeCoalesce(String entityParam, String value, Boolean isLowerCase, String defaultValue) {
        return ((root, criteriaQuery, criteriaBuilder) ->
        {
            String likeStr = isLowerCase ? "%" + value.toLowerCase() + "%" : "%" + value + "%";
            String safeDefaultValue = defaultValue != null ? defaultValue : "";
            String[] pathList = entityParam.split("\\.");
            Path<String> path = root.get(pathList[0]);
            for (int i = 1; i < pathList.length; i++) {
                path = path.get(pathList[i]);
            }
            return isLowerCase ?
                    criteriaBuilder.notLike(criteriaBuilder.coalesce(criteriaBuilder.lower(path), criteriaBuilder.literal(safeDefaultValue)), likeStr) :
                    criteriaBuilder.notLike(criteriaBuilder.coalesce(path, criteriaBuilder.literal(safeDefaultValue)), likeStr);
        });
    }

    default Specification<T> notLikeJoin(String joinedEntityPath, JoinType joinType,
                                      String entityParam, String value, Boolean isLowerCase){
        return ((root, criteriaQuery, criteriaBuilder) ->
        {
            Join<T,?> joinedEntity = root.join(joinedEntityPath, joinType);
            String likeStr = isLowerCase ? "%"+ value.toLowerCase() + "%" : "%" + value + "%";
            String[] pathList = entityParam.split("\\.");
            Path<String> path = joinedEntity.get(pathList[0]);
            for (int i = 1; i < pathList.length; i++) {
                path = path.get(pathList[i]);
            }
            return isLowerCase ? criteriaBuilder.like(criteriaBuilder.lower(path), likeStr):
                    criteriaBuilder.notLike(path, likeStr);
        });
    }

    default Specification<T> notLikeJoinCoalesce(String joinedEntityPath, JoinType joinType,
                                              String entityParam, String value, Boolean isLowerCase, String defaultValue) {
        return ((root, criteriaQuery, criteriaBuilder) ->
        {
            Join<T, ?> joinedEntity = root.join(joinedEntityPath, joinType);
            String likeStr = isLowerCase ? "%" + value.toLowerCase() + "%" : "%" + value + "%";
            String safeDefaultValue = defaultValue != null ? defaultValue : "";
            String[] pathList = entityParam.split("\\.");
            Path<String> path = joinedEntity.get(pathList[0]);
            for (int i = 1; i < pathList.length; i++) {
                path = path.get(pathList[i]);
            }
            return isLowerCase ?
                    criteriaBuilder.notLike(criteriaBuilder.coalesce(criteriaBuilder.lower(path), criteriaBuilder.literal(safeDefaultValue)), likeStr) :
                    criteriaBuilder.notLike(criteriaBuilder.coalesce(path, criteriaBuilder.literal(defaultValue)), likeStr);
        });
    }

}
