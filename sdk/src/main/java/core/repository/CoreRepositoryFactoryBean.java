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
 *  *  *  *  *  *  Written by Said Baysal <said_baysal@hotmail.com>, 5 2025
 *  *  *  *  *
 *  *  *  *
 *  *  *
 *  *
 *
 */

package core.repository;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.lang.NonNull;

import jakarta.persistence.EntityManager;

public class CoreRepositoryFactoryBean<T extends Repository<S,ID>, S, ID> extends JpaRepositoryFactoryBean<T, S, ID> {

    public CoreRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    @NonNull
    protected RepositoryFactorySupport createRepositoryFactory(@NonNull EntityManager entityManager) {
        return new CoreRepositoryFactory(entityManager);
    }
}
