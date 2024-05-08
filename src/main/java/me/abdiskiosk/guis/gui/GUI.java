package me.abdiskiosk.guis.gui;

import lombok.Getter;
import lombok.Setter;
import me.abdiskiosk.guis.GUIManager;
import me.abdiskiosk.guis.event.GUIClickEventHandler;
import me.abdiskiosk.guis.event.GUIEventHandler;
import me.abdiskiosk.guis.gui.view.GUIView;
import me.abdiskiosk.guis.gui.view.ListenerItemStack;
import me.abdiskiosk.guis.gui.view.bukkit.BukkitGUIView;
import me.abdiskiosk.guis.item.gui.GUIItem;
import me.abdiskiosk.guis.placeholder.PlaceholderUtils;
import me.abdiskiosk.guis.state.NamedState;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class GUI implements GUIEventHandler {

    private final @NotNull GUIView view;

    @Setter @Getter
    private @Nullable Consumer<@NotNull InventoryDragEvent> dragAction;
    @Setter @Getter
    private @Nullable Consumer<@NotNull InventoryClickEvent> clickAction;
    @Setter @Getter
    private @Nullable Consumer<@NotNull InventoryCloseEvent> closeAction;
    @Setter @Getter
    private @Nullable Consumer<@NotNull InventoryOpenEvent> openAction;
    @Setter @Getter
    private @Nullable Consumer<@NotNull Event> otherAction;

    protected final @NotNull Set<NamedState<?>> placeholders = new HashSet<>();

    public GUI(@NotNull String name, int sizeSlots) {
        this.view = createView(parseName(name), sizeSlots);
    }

    public synchronized ListenerItemStack set(@NotNull GUIItem item) {
        GUIItem withPlaceholders = PlaceholderUtils.withPlaceholders(item,
                GUIManager.getInstance().getPlaceholderApplier(), placeholders);
        return view.setItem(withPlaceholders);
    }

    protected void remove(@NotNull GUIItem item) {
        view.removeItems(item.getSlots());
    }

    public synchronized void registerPlaceholder(@NotNull NamedState<?> placeholder) {
        placeholders.add(placeholder);
    }

    protected @NotNull GUIView createView(@NotNull String name, int sizeSlots) {
        return new BukkitGUIView(parseName(name), sizeSlots);
    }

    protected void setName(@NotNull String name) {
        view.setName(parseName(name));
    }

    protected String parseName(@NotNull String name) {
        return GUIManager.getInstance().getPlaceholderApplier().replace(name, placeholders);
    }

    @Override
    public void handleDrag(@NotNull InventoryDragEvent event) {
        view.handleDrag(event);
        handleIfPresent(dragAction, event);
    }

    @Override
    public void handleClick(@NotNull InventoryClickEvent event) {
        view.handleClick(event);
        handleIfPresent(clickAction, event);
    }

    @Override
    public void handleOpen(@NotNull InventoryOpenEvent event) {
        handleIfPresent(openAction, event);
    }

    @Override
    public void handleClose(@NotNull InventoryCloseEvent event) {
        handleIfPresent(closeAction, event);
    }

    private <T> void handleIfPresent(Consumer<@NotNull T> action, @NotNull T event) {
        if(action != null) {
            action.accept(event);
        }
    }

}