package me.abdiskiosk.guis.pagination.item;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface PaginationItemConverter<T> {

    @NotNull PaginationItem convert(@NotNull T object);

}