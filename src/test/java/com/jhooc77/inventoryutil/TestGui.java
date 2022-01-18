package com.jhooc77.inventoryutil;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestGui {

    private JavaPlugin plugin;
    private PlayerMock testPlayer;

    @Before
    public void setup() {
        ServerMock server = MockBukkit.mock();
        plugin = MockBukkit.load(Main.class);
        testPlayer = server.addPlayer();
    }

    @Test
    public void testPageableGui() {

        List<String> exampleList = Arrays.asList("test text", "test text number2", "test text number3");
        Gui.PageableGui<String> pageableGui = new Gui.PageableGui<String>(36, "Inventory Name", exampleList, 1, 3) {
            @Override
            public ItemStack apply(String str) {
                ItemStack item = ItemUtil.Builder.builder().setMaterial(Material.PAPER).setDisplayName(str).build();
                addGuiCallback(item, action -> {
                    action.getWhoClicked().sendMessage("You clicked '" + str + "' !!");
                });
                return item;
            }

            @Override
            protected int getPreviousPageSlot() {
                return super.getPreviousPageSlot();
            }

            @Override
            protected int getNextPageSlot() {
                return super.getNextPageSlot();
            }

            @Override
            protected int getCurrentPageSlot() {
                return super.getCurrentPageSlot();
            }

            @Override
            public ItemStack[] getDefaultContents() {
                ItemStack[] defaultItems = new ItemStack[inventory.getSize()];
                defaultItems[0] = ItemUtil.Builder.builder().setDisplayName("default Pane").setMaterial(Material.CYAN_STAINED_GLASS_PANE).build();
                return defaultItems;
            }
        };
        pageableGui.open(testPlayer);

        Assertions.assertEquals(testPlayer.getOpenInventory().getTopInventory().getSize(), 36);
        Assertions.assertEquals(testPlayer.getOpenInventory().getTopInventory().getItem(1).getItemMeta().getDisplayName(), "test text");
        Assertions.assertEquals(testPlayer.getOpenInventory().getTopInventory().getItem(0).getItemMeta().getDisplayName(), "default Pane");
    }

    @After
    public void tearDown() {
        MockBukkit.unmock();
    }

}
