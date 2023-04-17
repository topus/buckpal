package io.reflectoring.railway;

import java.util.function.Function;

// https://en.wikipedia.org/wiki/Result_type
public sealed interface Result<S, F> permits Result.Success, Result.Failure {
    static <S, F> Result<S, F> success(S value) { return new Success<>(value); }
    static <S, F> Result<S, F> failure(F value) { return new Failure<>(value); }

    // Both functions have a common result supertype
    // e.g. `T` can be a `Result<X,Y>` or a resolved type like a `String` / `Request`
    <R, RS extends R, RF extends R> R either(Function<S, RS> onSuccess, Function<F, RF> onFailure);

    default <R> R then(Function<Result<S,F>, R> function) { return function.apply(this); }

    record Success<S, F>(S value) implements Result<S, F> {
        @Override
        public <R, RS extends R, RF extends R> R either(Function<S, RS> onSuccess, Function<F, RF> onFailure) {
            return onSuccess.apply(value);
        }
    }

    record Failure<S, F>(F failure) implements Result<S, F> {
        @Override
        public <R, RS extends R, RF extends R> R either(Function<S, RS> onSuccess, Function<F, RF> onFailure) {
            return onFailure.apply(failure);
        }
    }
}