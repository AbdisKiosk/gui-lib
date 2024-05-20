package me.abdiskiosk.guis.pagination.object;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class StaticPaginationObjectProvider<T> implements PaginationObjectProvider<T> {

    private final List<T> objects;

    public StaticPaginationObjectProvider(@NotNull List<T> objects) {
        this.objects = new ArrayList<>(objects);
    }

    @Override
    public @NotNull CompletableFuture<List<@NotNull T>> get(int minIndexInclusive, int maxIndexInclusive) {
        return CompletableFuture.completedFuture(objects.stream()
                .skip(minIndexInclusive)
                .limit(maxIndexInclusive - minIndexInclusive + 1)
                .collect(Collectors.toList()));
    }
}
