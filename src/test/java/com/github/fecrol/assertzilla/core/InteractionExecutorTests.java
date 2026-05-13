package com.github.fecrol.assertzilla.core;

import com.github.fecrol.assertzilla.core.exceptions.TestFailedException;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class InteractionExecutorTests {

    @Nested
    @Tags({
            @Tag("unit")
    })
    @DisplayName("Interaction Execution Result Tests")
    class InteractionExecutionResultTests {

        @Test
        @Tags({
                @Tag("TC-0001")
        })
        @DisplayName("TC-0001 Interaction without exception should not register a Throwable")
        void interactionWithoutExceptionShouldNotRegisterThrowable() {
            InteractionExecutor.InteractionExecutionResult interactionExecutionResult = InteractionExecutor
                    .InteractionExecutionResult
                    .forInteraction(new InteractionWithoutException());

            assertThat(interactionExecutionResult.isThrowableThrown(), is(false));
            assertThat(interactionExecutionResult.getThrownThrowable(), is(equalTo(null)));
        }

        @Test
        @Tags({
                @Tag("TC-0002")
        })
        @DisplayName("TC-0002 Interaction with exception should register a Throwable")
        void interactionWithExceptionShouldRegisterThrowable() {
            InteractionExecutor.InteractionExecutionResult interactionExecutionResult = InteractionExecutor
                    .InteractionExecutionResult
                    .forInteraction(new InteractionWithException());

            assertThat(interactionExecutionResult.isThrowableThrown(), is(true));
            assertThat(interactionExecutionResult.getThrownThrowable(), is(instanceOf(TestFailedException.class)));
        }
    }
}
