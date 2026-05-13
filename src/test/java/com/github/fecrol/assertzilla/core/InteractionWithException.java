package com.github.fecrol.assertzilla.core;

import com.github.fecrol.assertzilla.core.exceptions.TestFailedException;

public class InteractionWithException implements Interaction {

    @Override
    public void execute() {
        throw new TestFailedException("test failed");
    }
}
