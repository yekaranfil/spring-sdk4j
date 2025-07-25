package core.query;

import com.querydsl.jpa.impl.JPAQuery;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface CoreQueryCustomizer<T> extends Consumer<JPAQuery<T>> {
    default CoreQueryCustomizer<T> andThen(CoreQueryCustomizer<T> after) {
        Objects.requireNonNull(after);
        return query -> {
            this.accept(query);
            after.accept(query);
        };
    }
}
