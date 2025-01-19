package me.abdiskiosk.guis.util;

import lombok.Getter;
import me.abdiskiosk.guis.GUIManager;
import me.abdiskiosk.guis.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class Scheduler {

    public static void nextTick(@NotNull Runnable runnable) {
        Bukkit.getScheduler().runTaskLater(GUIManager.getPlugin(), runnable, 1L);
    }

    /**
     * @deprecated Use {@link GUI#whileOpenAsync(Runnable, int)} instead. This method does not work when the GUI is closed.
     */
    @Deprecated
    public static void whileOpen(@NotNull GUI gui, @NotNull Runnable runnable, int waitTicks) {
        untilClosed(gui, runnable, waitTicks);
    }

    /**
     * @deprecated Use {@link GUI#whileOpenAsync(Runnable, int)} instead. This method does not work when the GUI is closed.
     */
    @Deprecated
    public static void whileOpenAsync(@NotNull GUI gui, @NotNull Runnable runnable, int waitTicks) {
        untilClosedAsync(gui, runnable, waitTicks);
    }

    public static @NotNull BukkitTask untilClosed(@NotNull GUI gui, @NotNull Runnable runnable, int waitTicks) {
        return new WhileOpenTask(gui, runnable).runTaskTimer(GUIManager.getPlugin(), 0, waitTicks);
    }

    public static @NotNull BukkitTask untilClosedAsync(@NotNull GUI gui, @NotNull Runnable runnable, int waitTicks) {
        return new WhileOpenTask(gui, runnable).runTaskTimerAsynchronously(GUIManager.getPlugin(), 0, waitTicks);

    }

    public static void async(@NotNull Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(GUIManager.getPlugin(), runnable);
    }

    public static <T> CompletableFuture<T> async(@NotNull Supplier<T> supplier) {
        CompletableFuture<T> future = new CompletableFuture<>();
        Bukkit.getScheduler().runTaskAsynchronously(GUIManager.getPlugin(), () -> {
            future.complete(supplier.get());
        });
        return future;
    }

    public static void sync(@NotNull Runnable runnable) {
        if(Bukkit.isPrimaryThread()) {
            runnable.run();
            return;
        }
        Bukkit.getScheduler().runTask(GUIManager.getPlugin(), runnable);
    }

    public static class WhileOpenTask extends BukkitRunnable {

        private final GUI gui;
        private final Runnable run;

        private boolean firstRun = true;

        public WhileOpenTask(@NotNull GUI gui, @NotNull Runnable run) {
            this.gui = gui;
            this.run = run;
        }

        @Override
        public void run() {
            if(firstRun) {
                run.run();
                firstRun = false;
            }

            if(!gui.isOpen()) {
                cancel();
            }
            run.run();
        }

    }

    public static class Task extends BukkitRunnable {

        private final Runnable run;
        private final int waitTicks;
        private final GUI gui;
        private final boolean async;
        private final boolean firstRun;
        @Getter
        private boolean cancelled;

        public Task(@NotNull GUI gui, int waitTicks, boolean async, boolean firstRun, @NotNull Runnable run) {
            this.gui = gui;
            this.run = run;
            this.async = async;
            this.waitTicks = waitTicks;
            this.firstRun = firstRun;
        }

        @Override
        public void run() {
            run.run();
            if(firstRun || gui.isOpen()) {
                if(async) {
                    new Task(gui, waitTicks, async, false, run).runTaskLaterAsynchronously(GUIManager.getPlugin(), waitTicks);
                } else {
                    new Task(gui, waitTicks, async, false, run).runTaskLater(GUIManager.getPlugin(), waitTicks);
                }
            }
        }
    }
}
