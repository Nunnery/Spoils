package me.topplethenun.spoils.api;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public abstract class SpoilsPlugin extends JavaPlugin {

    /**
     * Writes debug messages to the plugin's debug log using the given {@link java.util.logging.Level}.
     * @param level Level of logging
     * @param messages messages to write
     */
    public abstract void debug(Level level, String... messages);

    /**
     * Writes debug messages to the plugin's debug log using {@link java.util.logging.Level#INFO}.
     * @param messages messages to write
     */
    public abstract void debug(String... messages);

}
