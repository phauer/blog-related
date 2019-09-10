package com.phauer.modernunittesting;

import org.awaitility.core.ConditionFactory;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

public class AwaitilityTest {
    private static final ConditionFactory WAIT = await()
            .atMost(Duration.ofSeconds(6))
            .pollInterval(Duration.ofSeconds(1))
            .pollDelay(Duration.ofSeconds(1));

    @Test
    public void waitAndPoll() {
        triggerAsyncEvent();
        WAIT.untilAsserted(() -> {
            assertThat(findInDatabase(1).getState()).isEqualTo(State.SUCCESS);
        });
    }

    private void triggerAsyncEvent() {

    }

    private Thread findInDatabase(int i) {
        return null;
    }

    enum State {
        SUCCESS
    }
}
