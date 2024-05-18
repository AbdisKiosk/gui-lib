package me.abdiskiosk.guis.pagination.index;

import me.abdiskiosk.guis.pagination.item.PaginationItem;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface IndexPaginationItemProvider {

    @NotNull PaginationItem provide(int index) throws IndexOutOfBoundsException;

}