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

import java.util.function.Function;

public final class CoreSpecsUtils {

    private CoreSpecsUtils() {

    }

    public static <T, V> void andIfNotNull(CoreSpecs<T> builder, V value, Function<V, Specification<T>> specFunction) {
        if (value != null) {
            builder.and(specFunction.apply(value));
        }
    }

    public static <T, V> void orIfNotNull(CoreSpecs<T> builder, V value, Function<V, Specification<T>> specFunction) {
        if (value != null) {
            builder.or(specFunction.apply(value));
        }
    }

    public static <T> void andIfNotBlank(CoreSpecs<T> builder, String value, Function<String, Specification<T>> specFunction) {
        if (value != null && !value.trim().isEmpty()) {
            builder.and(specFunction.apply(value));
        }
    }

    public static <T> void orIfNotBlank(CoreSpecs<T> builder, String value, Function<String, Specification<T>> specFunction) {
        if (value != null && !value.trim().isEmpty()) {
            builder.or(specFunction.apply(value));
        }
    }
}
