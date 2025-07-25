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

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.lang.NonNull;

import jakarta.persistence.EntityManager;

public class CoreRepositoryFactory extends JpaRepositoryFactory {

    public CoreRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    @NonNull
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        if (CoreRepository.class.isAssignableFrom(metadata.getRepositoryInterface())) {
            return CoreRepositoryImpl.class;
        }
        return super.getRepositoryBaseClass(metadata);
    }
}
