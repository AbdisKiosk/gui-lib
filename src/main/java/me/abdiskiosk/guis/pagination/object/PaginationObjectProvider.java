package me.abdiskiosk.guis.pagination.object;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface PaginationObjectProvider<T> {

    @NotNull CompletableFuture<List<@NotNull T>> get(int minIndexInclusive, int maxIndexInclusive);

}