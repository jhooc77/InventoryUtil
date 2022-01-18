package com.jhooc77.inventoryutil;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Gui implements InventoryHolder {

	private static JavaPlugin plugin;
	protected Inventory inventory;
	protected Map<ItemStack, Consumer<InventoryClickEvent>> itemConsumer = new HashMap<>();
	protected Map<Integer, Consumer<InventoryClickEvent>> slotConsumer = new HashMap<>();
	
	protected Consumer<InventoryClickEvent> generalConsumer = action -> {};
	
	protected Consumer<InventoryCloseEvent> closeConsumer = action -> {};
	
	protected Consumer<InventoryOpenEvent> openConsumer = action -> {};
	
	protected boolean cancelled = true;
	
	public Gui(InventoryType inventoryType, String title) {
		this.inventory = plugin.getServer().createInventory(this, inventoryType, title);
	}

	public Gui(InventoryType inventoryType) {
		this.inventory = plugin.getServer().createInventory(this, inventoryType);
	}

	public Gui(int inventorySize, String title) {
		this.inventory = plugin.getServer().createInventory(this, inventorySize, title);
	}

	public Gui(int inventorySize) {
		this.inventory = plugin.getServer().createInventory(this, inventorySize);
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	public void open(Player player) {
		player.openInventory(inventory);
	}

	public void addGuiCallback(ItemStack item, Consumer<InventoryClickEvent> action) {
		itemConsumer.put(item, action);
	}

	public void addGuiCallback(int slot, Consumer<InventoryClickEvent> action) {
		slotConsumer.put(slot, action);
	}

	public void setGuiCallback(Consumer<InventoryClickEvent> action) {
		generalConsumer = action;
	}

	public Consumer<InventoryClickEvent> onClick(ItemStack item) {
		return itemConsumer.get(item);
	}

	public Consumer<InventoryClickEvent> onClick(int slot) {
		return slotConsumer.get(slot);
	}

	public Consumer<InventoryClickEvent> onClick() {
		return generalConsumer;
	}
	
	public void setCloseCallback(Consumer<InventoryCloseEvent> action) {
		closeConsumer = action;
	}
	
	public void setOpenCallback(Consumer<InventoryOpenEvent> action) {
		openConsumer = action;
	}
	
	public Consumer<InventoryCloseEvent> onClose() {
		return closeConsumer;
	}

	public Consumer<InventoryOpenEvent> onOpen() {
		return openConsumer;
	}

	public static Gui getGui(Inventory inventory) {
		if (inventory.getHolder() instanceof Gui gui) {
			return gui;
		} else {
			return null;
		}
	}
	
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	public boolean isCancelled() {
		return this.cancelled;
	}
	
	public static abstract class PageableGui<T> extends Gui implements Function<T, ItemStack>{
		
		final List<T> list;
		final ItemStack[] defaultContents;
		final int[] indexes;
		int currentPage;

		public PageableGui(InventoryType inventoryType, String title, List<T> list, int...indexes) {
			this(inventoryType, title, list, null, indexes);
		}

		public PageableGui(InventoryType inventoryType, String title, Collection<T> list, int...indexes) {
			this(inventoryType, title, list, null, indexes);
		}

		public PageableGui(InventoryType inventoryType, String title, List<T> list, Predicate<T> predicate, int...indexes) {
			super(inventoryType, title);
			if (inventory.getSize() == 9) {
				throw new UnsupportedOperationException();
			}
			if (predicate != null) {
				this.list = list.stream().filter(predicate).collect(Collectors.toList());
			} else {
				this.list = list;
			}
			if (indexes.length == 0) {
				this.indexes = new int[inventory.getSize()-9];
				for (int i = 0; i < inventory.getSize()-9; i++) {
					this.indexes[i] = i;
				}
			} else {
				if (Arrays.stream(indexes).anyMatch(number -> number >= inventory.getSize()-9)) throw new UnsupportedOperationException();
				this.indexes = indexes;
			}
			defaultContents = inventory.getContents();
			setPage(1);
		}
		
		public PageableGui(InventoryType inventoryType, String title, Collection<T> list, Predicate<T> predicate, int...indexes) {
			super(inventoryType, title);
			if (inventory.getSize() == 9) {
				throw new UnsupportedOperationException();
			}
			if (predicate != null) {
				this.list = list.stream().filter(predicate).collect(Collectors.toList());
			} else {
				this.list = new ArrayList<>(list);
			}
			if (indexes.length == 0) {
				this.indexes = new int[inventory.getSize()-9];
				for (int i = 0; i < inventory.getSize()-9; i++) {
					this.indexes[i] = i;
				}
			} else {
				if (Arrays.stream(indexes).anyMatch(number -> number >= inventory.getSize()-9)) throw new UnsupportedOperationException();
				this.indexes = indexes;
			}
			defaultContents = inventory.getContents();
			setPage(1);
		}

		public PageableGui(int inventorySize, String title, List<T> list, int...indexes) {
			this(inventorySize, title, list, null, indexes);
		}

		public PageableGui(int inventorySize, String title, Collection<T> list, int...indexes) {
			this(inventorySize, title, list, null, indexes);
		}

		public PageableGui(int inventorySize, String title, List<T> list, Predicate<T> predicate, int...indexes) {
			super(inventorySize, title);
			if (inventory.getSize() == 9) {
				throw new UnsupportedOperationException();
			}
			if (predicate != null) {
				this.list = list.stream().filter(predicate).collect(Collectors.toList());
			} else {
				this.list = list;
			}
			if (indexes.length == 0) {
				this.indexes = new int[inventory.getSize()-9];
				for (int i = 0; i < inventory.getSize()-9; i++) {
					this.indexes[i] = i;
				}
			} else {
				if (Arrays.stream(indexes).anyMatch(number -> number >= inventory.getSize()-9)) throw new UnsupportedOperationException();
				this.indexes = indexes;
			}
			defaultContents = inventory.getContents();
			setPage(1);
		}

		public PageableGui(int inventorySize, String title, Collection<T> list, Predicate<T> predicate, int...indexes) {
			super(inventorySize, title);
			if (inventory.getSize() == 9) {
				throw new UnsupportedOperationException();
			}
			if (predicate != null) {
				this.list = list.stream().filter(predicate).collect(Collectors.toList());
			} else {
				this.list = new ArrayList<>(list);
			}
			if (indexes.length == 0) {
				this.indexes = new int[inventory.getSize()-9];
				for (int i = 0; i < inventory.getSize()-9; i++) {
					this.indexes[i] = i;
				}
			} else {
				if (Arrays.stream(indexes).anyMatch(number -> number >= inventory.getSize()-9)) throw new UnsupportedOperationException();
				this.indexes = indexes;
			}
			defaultContents = inventory.getContents();
			setPage(1);
		}

		public PageableGui(int inventorySize, List<T> list, int...indexes) {
			this(inventorySize, list, null, indexes);
		}

		public PageableGui(int inventorySize, Collection<T> list, int...indexes) {
			this(inventorySize, list, null, indexes);
		}

		public PageableGui(int inventorySize, List<T> list, Predicate<T> predicate, int...indexes) {
			super(inventorySize);
			if (inventory.getSize() == 9) {
				throw new UnsupportedOperationException();
			}
			if (predicate != null) {
				this.list = list.stream().filter(predicate).collect(Collectors.toList());
			} else {
				this.list = list;
			}
			if (indexes.length == 0) {
				this.indexes = new int[inventory.getSize()-9];
				for (int i = 0; i < inventory.getSize()-9; i++) {
					this.indexes[i] = i;
				}
			} else {
				if (Arrays.stream(indexes).anyMatch(number -> number >= inventory.getSize()-9)) throw new UnsupportedOperationException();
				this.indexes = indexes;
			}
			defaultContents = inventory.getContents();
			setPage(1);
		}

		public PageableGui(int inventorySize, Collection<T> list, Predicate<T> predicate, int...indexes) {
			super(inventorySize);
			if (inventory.getSize() == 9) {
				throw new UnsupportedOperationException();
			}
			if (predicate != null) {
				this.list = list.stream().filter(predicate).collect(Collectors.toList());
			} else {
				this.list = new ArrayList<>(list);
			}
			if (indexes.length == 0) {
				this.indexes = new int[inventory.getSize()-9];
				for (int i = 0; i < inventory.getSize()-9; i++) {
					this.indexes[i] = i;
				}
			} else {
				if (Arrays.stream(indexes).anyMatch(number -> number >= inventory.getSize()-9)) throw new UnsupportedOperationException();
				this.indexes = indexes;
			}
			defaultContents = inventory.getContents();
			setPage(1);
		}

		public PageableGui(InventoryType inventoryType, List<T> list, int...indexes) {
			this(inventoryType, list, null, indexes);
		}

		public PageableGui(InventoryType inventoryType, Collection<T> list, int...indexes) {
			this(inventoryType, list, null, indexes);
		}

		public PageableGui(InventoryType inventoryType, List<T> list, Predicate<T> predicate, int...indexes) {
			super(inventoryType);
			if (inventory.getSize() == 9) {
				throw new UnsupportedOperationException();
			}
			if (predicate != null) {
				this.list = list.stream().filter(predicate).collect(Collectors.toList());
			} else {
				this.list = list;
			}
			if (indexes.length == 0) {
				this.indexes = new int[inventory.getSize()-9];
				for (int i = 0; i < inventory.getSize()-9; i++) {
					this.indexes[i] = i;
				}
			} else {
				if (Arrays.stream(indexes).anyMatch(number -> number >= inventory.getSize()-9)) throw new UnsupportedOperationException();
				this.indexes = indexes;
			}
			defaultContents = inventory.getContents();
			setPage(1);
		}

		public PageableGui(InventoryType inventoryType, Collection<T> list, Predicate<T> predicate, int...indexes) {
			super(inventoryType);
			if (inventory.getSize() == 9) {
				throw new UnsupportedOperationException();
			}
			if (predicate != null) {
				this.list = list.stream().filter(predicate).collect(Collectors.toList());
			} else {
				this.list = new ArrayList<>(list);
			}
			if (indexes.length == 0) {
				this.indexes = new int[inventory.getSize()-9];
				for (int i = 0; i < inventory.getSize()-9; i++) {
					this.indexes[i] = i;
				}
			} else {
				if (Arrays.stream(indexes).anyMatch(number -> number >= inventory.getSize()-9)) throw new UnsupportedOperationException();
				this.indexes = indexes;
			}
			defaultContents = inventory.getContents();
			setPage(1);
		}

		protected int getPreviousPageSlot() {
			return inventory.getSize()-9;
		}

		protected int getNextPageSlot() {
			return inventory.getSize()-1;
		}

		protected int getCurrentPageSlot() {
			return inventory.getSize()-5;
		}
		
		public final boolean setPage(int page) {
			currentPage = page;
			inventory.setContents(getDefaultContents());
			int indexOfPage = indexes.length;
			if (list.size() > indexOfPage*(page-1)) {
				
				int key = list.size() > indexOfPage*(page)?indexOfPage*(page):list.size();
				
				for(int i = indexOfPage*(page-1); i < key; i++) {
					inventory.setItem(indexes[i-indexOfPage*(page-1)] ,apply(list.get(i)));
				}
				
				if (list.size() > indexOfPage*(page) && getNextPageSlot() != -1) {
					inventory.setItem(getNextPageSlot(), ItemUtil.Builder.builder().setMaterial(Material.GREEN_STAINED_GLASS_PANE).setDisplayName("§aNext Page").build());
					slotConsumer.put(getNextPageSlot(), event -> {
						setPage(page+1);
					});
				} else {
					slotConsumer.remove(getNextPageSlot());
				}
				if (page > 1 && getPreviousPageSlot() != -1) {
					inventory.setItem(getPreviousPageSlot(), ItemUtil.Builder.builder().setMaterial(Material.RED_STAINED_GLASS_PANE).setDisplayName("§cPrevious Page").build());
					slotConsumer.put(getPreviousPageSlot(), event -> {
						setPage(page-1);
					});
				} else {
					slotConsumer.remove(getPreviousPageSlot());
				}
				if (getCurrentPageSlot() != -1) inventory.setItem(getCurrentPageSlot(), ItemUtil.Builder.builder().setMaterial(Material.BOOK).setDisplayName("§e현재 페이지: " + page).build());
				return true;
			} else {
				return false;
			}
		}

		public ItemStack[] getDefaultContents() {
			return defaultContents;
		}

		public final int getPage() {
			return currentPage;
		}

		@Override
		public abstract ItemStack apply(T t);
		
	}

	public static class BooleanGui<T> extends Gui {

		ItemStack yes;
		ItemStack no;

		public BooleanGui() {
			this(null);
		}

		public BooleanGui(List<String> description) {
			this(description, null, null);
		}

		public BooleanGui(List<String> description, String trueDescription, String falseDescription) {
			super(27, "confirm");
			inventory.setItem(11, yes = ItemUtil.Builder.builder().setMaterial(Material.GREEN_STAINED_GLASS).setDisplayName("§a§lYes").setLore(trueDescription!=null?new String[] {trueDescription}:new String[0]).build());
			if (description != null) {
				inventory.setItem(13, ItemUtil.Builder.builder().setMaterial(Material.BOOK).setDisplayName(description.get(0)).setLore(description.size() > 1?description.subList(1, description.size()-1):Collections.EMPTY_LIST).build());
			}
			inventory.setItem(15, no = ItemUtil.Builder.builder().setMaterial(Material.RED_STAINED_GLASS).setDisplayName("§c§lNo").setLore(falseDescription!=null?new String[] {falseDescription}:new String[0]).build());
		}

		public void falseCallback(Consumer<InventoryClickEvent> action) {
			addGuiCallback(no, action);
		}

		public void trueCallback(Consumer<InventoryClickEvent> action) {
			addGuiCallback(yes, action);
		}
	}

	static final class GuiHandler implements Listener {

		GuiHandler(JavaPlugin plugin) {
			Gui.plugin = plugin;
		}

		@EventHandler
		public void onInventoryOpen(InventoryOpenEvent event) {
			Gui gui = Gui.getGui(event.getInventory());
			if (gui == null) return;
			gui.onOpen().accept(event);
		}

		@EventHandler
		public void onInventoryClick(InventoryClickEvent event) {
			Gui gui = Gui.getGui(event.getInventory());
			if (gui == null) return;
			event.setCancelled(event.isCancelled() || gui.isCancelled());
			Consumer<InventoryClickEvent> callback1 = gui.onClick(event.getRawSlot());
			Consumer<InventoryClickEvent> callback2 = gui.onClick(event.getCurrentItem());
			gui.onClick().accept(event);
			if (callback1 != null) {
				callback1.accept(event);
			}
			if (callback2 != null) {
				callback2.accept(event);
			}
		}

		@EventHandler
		public void onInventoryDrag(InventoryDragEvent event) {
			Gui gui = Gui.getGui(event.getInventory());
			if (gui == null) return;
			event.setCancelled(gui.isCancelled());
		}

		@EventHandler
		public void onClose(InventoryCloseEvent event) {
			Gui gui = Gui.getGui(event.getInventory());
			if (gui == null) return;
			gui.onClose().accept(event);
		}
	}

}
