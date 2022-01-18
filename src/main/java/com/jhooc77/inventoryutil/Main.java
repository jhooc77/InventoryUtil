package com.jhooc77.inventoryutil;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(new Gui.GuiHandler(this), this);

    }

}
