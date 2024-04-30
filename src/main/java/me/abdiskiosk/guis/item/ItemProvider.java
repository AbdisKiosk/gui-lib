package me.abdiskiosk.guis.item;

import me.abdiskiosk.guis.component.Component;
import me.abdiskiosk.guis.placeholder.PlaceholderApplier;
import me.abdiskiosk.guis.state.NamedState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class ItemProvider implements Component<ItemStack> {

    protected final PlaceholderApplier placeholderApplier;
    protected final List<NamedState<?>> placeholders;

    public ItemProvider(@NotNull PlaceholderApplier placeholderApplier, @NotNull List<NamedState<?>> placeholders) {
        this.placeholderApplier = placeholderApplier;
        this.placeholders = placeholders;
    }

    protected abstract @NotNull ItemStack generateBase();

    @Override
    public @NotNull ItemStack create() {
        ItemStack item = generateBase();
        updateMeta(item);
        return item;
    }

    protected void updateMeta(@NotNull ItemStack item) {
        if(!item.hasItemMeta()) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        if(meta.getDisplayName() != null) {
            meta.setDisplayName(placeholderApplier.replace(meta.getDisplayName(), placeholders));
        }
        if(meta.getLore() != null) {
            meta.setLore(placeholderApplier.replace(meta.getLore(), placeholders));
        }

    }

}