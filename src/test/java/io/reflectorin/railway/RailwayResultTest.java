package io.reflectorin.railway;

import io.reflectoring.railway.Result;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static io.reflectoring.railway.Result.Failure;
import static io.reflectoring.railway.Result.Success;

public class RailwayResultTest {

    @Test
    public void success_result_when_parsing_valid_number() {
        var sut = parse("10")
                .then(this::attemptToDouble);

        assertThat(sut).isEqualTo("Two times ten is equal to 20");
    }

    @Test
    public void failure_result_when_parsing_not_a_number() {
        var sut = parse("ten")
                .then(this::attemptToDouble);

        assertThat(sut).isEqualTo("java.lang.NumberFormatException: For input string: \"ten\"");
    }

    public Result<Integer, Exception> parse(String input) {
        try {
            return new Success<>(Integer.parseInt(input));
        } catch (NumberFormatException e) {
            return new Failure<>(e);
        }
    }

    private String attemptToDouble(Result<Integer, Exception> result) {
        return result.either(
                integer -> "Two times ten is equal to " + integer * 2,
                Throwable::toString
        );
    }
}
