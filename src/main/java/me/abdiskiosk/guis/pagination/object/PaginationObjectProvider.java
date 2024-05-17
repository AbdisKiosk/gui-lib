package me.abdiskiosk.guis.pagination.object;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@FunctionalInterface
public interface PaginationObjectProvider<T> {

    @NotNull List<@NotNull T> get(int minIndexInclusive, int maxIndexInclusive);

}