package me.abdiskiosk.guis.state.objects;

import me.abdiskiosk.guis.pagination.PaginationHandler;
import me.abdiskiosk.guis.state.State;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PageState implements State<Integer> {

    private final IntState state;
    private final PaginationHandler<?> paginationHandler;

    public PageState(PaginationHandler<?> paginationHandler) {
        this.state = new IntState(1);
        this.paginationHandler = paginationHandler;
        set(1);
    }

    @Override
    public Integer get() {
        return state.get();
    }

    @Override
    public void set(Integer value) {
        if(value < 0) {
            return;
        }
        if(paginationHandler.setPageIfNotEmpty(value)) {
            state.set(value);
        }
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
