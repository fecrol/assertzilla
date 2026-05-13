package com.github.fecrol.assertzilla.core;

import com.github.fecrol.assertzilla.core.exceptions.TestFailedException;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tags({
        @Tag("unit")
})
@DisplayName("Interaction Executor Tests")
public class InteractionExecutorTests {

    @Test
    @Tags({
            @Tag("TC-0003")
    })
    @DisplayName("TC-0003 No exception should be thrown when interactions without exception are executed")
    void noExceptionShouldBeThrownWhenInteractionsWithoutExceptionAreExecuted() {
        List<Interaction> listOfInteractionsWithoutException = List.of(
                new InteractionWithoutException(),
                new InteractionWithoutException()
        );

        assertDoesNotThrow(() -> InteractionExecutor.execute(listOfInteractionsWithoutException));
    }

    @Test
    @Tags({
            @Tag("TC-0004")
    })
    @DisplayName("TC-0004 Test Failed Exception should be thrown when interactions with exception are executed")
    void testFailedExceptionShouldBeThrownWhenInteractionsWithExceptionAreExecuted() {
        List<Interaction> listOfInteractionsWithInteractionThatThrowsException = List.of(
                new InteractionWithException(),
                new InteractionWithoutException()
        );

        assertThrows(
                TestFailedException.class,
                () -> InteractionExecutor.execute(listOfInteractionsWithInteractionThatThrowsException)
        );
    }

    @Nested
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
