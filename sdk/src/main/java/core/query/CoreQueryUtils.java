package core.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.util.StringUtils;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public final class CoreQueryUtils {

    private CoreQueryUtils() {
    }

    public static void setEntityGraph(TypedQuery<?> query, EntityManager entityManager, String entityGraphName) {
        if (StringUtils.hasText(entityGraphName)) {
            try {
                EntityGraph<?> entityGraph = entityManager.getEntityGraph(entityGraphName);
                query.setHint("jakarta.persistence.fetchgraph", entityGraph);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("EntityGraph not found with name: " + entityGraphName, e);
            }
        }
    }

    public static void setEntityGraph(JPAQuery<?> query, EntityManager entityManager, String entityGraphName) {
        if (StringUtils.hasText(entityGraphName)) {
            try {
                EntityGraph<?> entityGraph = entityManager.getEntityGraph(entityGraphName);
                query.setHint("jakarta.persistence.fetchgraph", entityGraph);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Tanımlı olmayan EntityGraph: " + entityGraphName, e);
            }
        }
    }

    public static void addIfNotBlank(BooleanBuilder b,
                                     String value,
                                     Function<String, Predicate> f) {
        Optional.ofNullable(value)
                .filter(s -> !s.trim().isEmpty())
                .map(f)
                .ifPresent(b::and);
    }

    public static void addIfNotBlankJsonb(BooleanBuilder b,
                                          String value,
                                          Function<String, BooleanExpression> f) {
        Optional.ofNullable(value)
                .filter(s -> !s.trim().isEmpty())
                .map(f)
                .ifPresent(b::and);
    }

    public static void addIfNotEmptyList(BooleanBuilder b,
                                         List<String> values,
                                         Function<List<String>, Predicate> f) {
        Optional.ofNullable(values)
                .filter(list -> !list.isEmpty())
                .map(f)
                .ifPresent(b::and);
    }

    public static <T> void addIfNotNull(BooleanBuilder b,
                                        T value,
                                        Function<T, Predicate> f) {
        Optional.ofNullable(value)
                .map(f)
                .ifPresent(b::and);
    }

    public static <T> OrderSpecifier<?> createOrderSpecifierFromString (
            Class<T> entityClass,
            String alias,
            String customSort
    )
    {
        String[] parts = customSort.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Sıralama yönü geçersiz. Sadece 'asc' veya 'desc' olmalı.");
        }
        String propertyPath = parts[0].trim();
        String direction = parts[1].trim().toLowerCase();

        PathBuilder<T> builder = new PathBuilder<>(entityClass, alias);
        Order order = direction.equals("asc") ? Order.ASC : Order.DESC;

        return new OrderSpecifier<>(
                order,
                builder.getComparable(propertyPath, Comparable.class)
        );
    }
}
