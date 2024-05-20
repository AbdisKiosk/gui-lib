package me.abdiskiosk.guis.util;

import me.abdiskiosk.guis.GUIManager;
import me.abdiskiosk.guis.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class Scheduler {

    public static void whileOpen(@NotNull GUI gui, @NotNull Runnable runnable, int waitTicks) {
        new Task(gui, waitTicks, runnable).runTask(GUIManager.getPlugin());
    }

    public static void async(@NotNull Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(GUIManager.getPlugin(), runnable);
    }

    public static void sync(@NotNull Runnable runnable) {
        Bukkit.getScheduler().runTask(GUIManager.getPlugin(), runnable);
    }

    public static class Task extends BukkitRunnable {

        private final Runnable run;
        private final int waitTicks;
        private final GUI gui;

        public Task(@NotNull GUI gui, int waitTicks, @NotNull Runnable run) {
            this.gui = gui;
            this.run = run;
            this.waitTicks = waitTicks;
        }

        @Override
        public void run() {
            run.run();
            if(gui.isOpen()) {
                runTaskLater(GUIManager.getPlugin(), waitTicks);
            }
        }
    }
}
