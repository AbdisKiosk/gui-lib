package me.abdiskiosk.guis.util;

import me.abdiskiosk.guis.GUIManager;
import me.abdiskiosk.guis.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class Scheduler {

    public static void whileOpen(@NotNull GUI gui, @NotNull Runnable runnable, int waitTicks) {
        new Task(gui, runnable).runTaskTimer(GUIManager.getPlugin(), 0, waitTicks);
    }

    public static class Task extends BukkitRunnable {

        private final Runnable run;
        private final GUI gui;

        public Task(@NotNull GUI gui, @NotNull Runnable run) {
            this.gui = gui;
            this.run = run;
        }

        @Override
        public void run() {
            if(!gui.isOpen()) {
                cancel();
                return;
            }
            run.run();
        }

    }
}
