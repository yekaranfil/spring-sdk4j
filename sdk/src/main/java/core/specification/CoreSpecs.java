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

package core.specification;

import org.springframework.data.jpa.domain.Specification;

public interface CoreSpecs<T> extends BetweenSpecs<T>, EmptySpecs<T>, EqualSpecs<T>, GreaterThanSpecs<T>, GreaterThanOrEqualSpecs<T>,
LessThanSpecs<T>, LessThanOrEqualSpecs<T>, LikeSpecs<T>, NotEmptySpecs<T>, NotLikeSpecs<T>, NotNullSpecs<T>, NullSpecs<T> {

    void and(Specification<T> spec);

    void or(Specification<T> spec);
}
