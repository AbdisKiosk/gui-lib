package me.abdiskiosk.guis.item;

import me.abdiskiosk.guis.placeholder.PlaceholderApplier;
import me.abdiskiosk.guis.state.NamedState;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StaticItemProvider extends ItemProvider {

    private final @NotNull ItemStack item;


    public StaticItemProvider(@NotNull PlaceholderApplier placeholderApplier, @NotNull List<NamedState<?>> placeholders,
                              @NotNull ItemStack item) {
        super(placeholderApplier, placeholders);
        this.item = item;
    }

    @Override
    protected @NotNull ItemStack generateBase() {
        return item.clone();
    }
}
