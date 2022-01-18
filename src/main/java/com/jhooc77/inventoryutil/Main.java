package com.jhooc77.inventoryutil;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class Main extends JavaPlugin {

    // MockBukkit Test
    public Main() {}
    protected Main(@org.jetbrains.annotations.NotNull JavaPluginLoader loader, @org.jetbrains.annotations.NotNull PluginDescriptionFile description, @org.jetbrains.annotations.NotNull File dataFolder, @org.jetbrains.annotations.NotNull File file) {super(loader, description, dataFolder, file); }

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new Gui.GuiHandler(this), this);

    }

}
