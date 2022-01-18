# InventoryUtil
Minecraft InventoryUtil using Buukkit API

# Usage

## Normal Inventory
``` Java
Gui gui = new Gui(36, "Inventory Name");
gui.addGuiCallback(1 -> {
    action.getWhoClicked().sendMessage("Clicked slot number 1");
}

```
## Pageable Inventory
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

    // Slot Number that next page display button will be set.
    // Default value is 'inventorysize -9'
    // -1 to disable button
    @Override
    protected int getPreviousPageSlot() {
        return super.getPreviousPageSlot();
    }
    
    // Slot Number that next page button will be set.
    // Default value is 'inventorysize -1'
    // -1 to disable button
    @Override
    protected int getNextPageSlot() {
        return super.getNextPageSlot();
    }

    // Slot Number that current page display button will be set.
    // Default value is 'inventorysize -5'
    // -1 to disable button
    @Override
    protected int getCurrentPageSlot() {
        return super.getCurrentPageSlot();
    }
    
    // Default Item Slots.
    // When the page changes, Inventory reset to this items.
    @Override
    public ItemStack[] getDefaultContents() {
        ItemStack[] defaultItems = new ItemStack[inventory.getSize()];
        defaultItems[0] = ItemUtil.Builder.builder().setDisplayName("default Pane").setMaterial(Material.CYAN_STAINED_GLASS_PANE).build();
        return defaultItems;
    }
};
pageableGui.open(player);
