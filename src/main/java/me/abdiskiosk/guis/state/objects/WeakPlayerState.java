package me.abdiskiosk.guis.state.objects;

import me.abdiskiosk.guis.state.DefaultState;
import me.abdiskiosk.guis.state.State;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class WeakPlayerState implements State<Player> {

    private final DefaultState<UUID> state;

    public WeakPlayerState(@NotNull Player player) {
        this.state = new DefaultState<>(player.getUniqueId());
    }


    @Override
    public Player get() {
        return Bukkit.getPlayer(state.get());
    }

    @Override
    public void set(Player value) {
        state.set(value.getUniqueId());
    }

    @Override
    public void update() {
        state.update();
    }

    @Override
    public void subscribe(@NotNull Consumer<Player> subscriber) {
        state.subscribe(uuid -> subscriber.accept(Bukkit.getPlayer(uuid)));
    }

    @Override
    public void subscribe(@NotNull BiConsumer<Player, Player> subscriber) {
        state.subscribe((uuid1, uuid2) -> subscriber.accept(Bukkit.getPlayer(uuid1), Bukkit.getPlayer(uuid2)));
    }

    @Override
    public void unsubscribe(@NotNull Consumer<Player> subscriber) {
        state.unsubscribe(uuid -> subscriber.accept(Bukkit.getPlayer(uuid)));
    }
}