package me.abdiskiosk.guis.test;

import me.abdiskiosk.guis.state.State;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class StateTests {

    private final ExecutorService executorService = Executors.newFixedThreadPool(12);

    @Test
    public void whenStateIsUpdated_thenSubscribersAreNotified() {
        AtomicBoolean called = new AtomicBoolean(false);

        State<Integer> state = new State<>(0);
        state.subscribe(update -> called.set(true));

        state.set(1);

        assert called.get();
    }

    @Test
    public void whenStateIsConcurrentlyUpdates_subscriberStateIsConsistent() throws InterruptedException {
        List<Integer> seen = Collections.synchronizedList(new ArrayList<>());
        State<Integer> state = new State<>(0);

        final int SUBSCRIBERS = 10;
        for(int i = 0; i < SUBSCRIBERS; i++) {
            state.subscribe(seen::add);
        }

        final int UPDATES = 12;
        for(int i = 0; i < UPDATES; i++) {
            int finalI = i;
            executorService.submit(() -> state.set(finalI));
        }

        executorService.awaitTermination(100, java.util.concurrent.TimeUnit.MILLISECONDS);

        assert seen.size() == UPDATES * SUBSCRIBERS;
    }

}