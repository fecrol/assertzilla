package com.github.fecrol.assertzilla.core;

import com.github.fecrol.assertzilla.core.exceptions.TestFailedException;

import java.util.List;

/**
 * @author Maciej Fec
 * @version 0.1.0
 * @since 0.1.0
 */
public class InteractionExecutor {

    public static void execute(List<Interaction> listOfInteractions) {
        boolean throwableThrown = false;
        Throwable thrownThrowable = null;

        for (Interaction interaction : listOfInteractions) {
            if (!throwableThrown) {
                InteractionExecutor.InteractionExecutionResult interactionExecutionResult = InteractionExecutor
                        .InteractionExecutionResult
                        .forInteraction(interaction);
                throwableThrown = interactionExecutionResult.isThrowableThrown();

                if (throwableThrown) {
                    thrownThrowable = interactionExecutionResult.getThrownThrowable();
                }
            }
        }

        if (throwableThrown) {
            throw new TestFailedException(thrownThrowable.getMessage());
        }
    }

    /**
     * @author Maciej Fec
     * @version 0.1.0
     * @since 0.1.0
     */
    public static class InteractionExecutionResult {

        private boolean throwableThrown;
        private Throwable thrownThrowable;

        private InteractionExecutionResult(Interaction interaction) {
            try {
                interaction.execute();
                throwableThrown = false;
                thrownThrowable = null;
            } catch (Throwable throwable) {
                throwableThrown = true;
                thrownThrowable = throwable;
            }
        }

        public static InteractionExecutor.InteractionExecutionResult forInteraction(Interaction interaction) {
            return new InteractionExecutor.InteractionExecutionResult(interaction);
        }

        public boolean isThrowableThrown() {
            return throwableThrown;
        }

        public Throwable getThrownThrowable() {
            return thrownThrowable;
        }
    }
}
