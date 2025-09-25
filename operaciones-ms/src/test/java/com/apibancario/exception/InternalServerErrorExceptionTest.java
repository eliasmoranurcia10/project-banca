package com.apibancario.exception;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class InternalServerErrorExceptionTest {
    @Test
    void testInternalServerErrorExceptionMessage() {
        assertThatThrownBy(
                () -> { throw new InternalServerErrorException("Internal error");
                }).isInstanceOf(InternalServerErrorException.class).hasMessage("Internal error");
    }
}
