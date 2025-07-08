package me.abdiskiosk.guis.state.objects;

import lombok.Getter;
import me.abdiskiosk.guis.pagination.PaginationHandler;
import me.abdiskiosk.guis.state.State;
import me.abdiskiosk.guis.util.Scheduler;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PageState implements State<Integer> {

    @Getter
    private final BooleanState loadingState;
    private final IntState state;
    private final PaginationHandler<?> paginationHandler;

    public PageState(PaginationHandler<?> paginationHandler) {
        this.state = new IntState(1);
        this.loadingState = new BooleanState(false);
        this.paginationHandler = paginationHandler;
        set(1);
    }


    @Override
    public Integer get() {
        return state.get();
    }

    @Override
    public synchronized void set(Integer value) {
        if(loadingState.get()) {
            return;
        }
        loadingState.set(true);
        if(value < 0) {
            return;
        }
        paginationHandler.attemptSetPage(value).thenAccept(result -> {
            if(!result.equals(PaginationHandler.PageResult.EMPTY)) {
                state.set(value);
            }
            loadingState.set(false);
        });
    }

    public void nextPage() {
        set(get() + 1);
    }

    public void previousPage() {
        set(get() - 1);
    }

    public void rerender() {
        paginationHandler.setPage(get());
    }

    @Override
    public void update() {
        state.update();
    }

    @Override
    public void subscribe(@NotNull Consumer<Integer> subscriber) {
        state.subscribe(subscriber);
    }

    @Override
    public void subscribe(@NotNull BiConsumer<Integer, Integer> subscriber) {
        state.subscribe(subscriber);
    }

    @Override
    public void unsubscribe(@NotNull Consumer<Integer> subscriber) {
        state.unsubscribe(subscriber);
    }
}
