package com.jhooc77.inventoryutil;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ItemUtil {
	public static ItemStack PANE;
	private static Random random = new Random();

	static {
		PANE = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta meta = PANE.getItemMeta();
		meta.setDisplayName(ChatColor.BLACK + "");
		PANE.setItemMeta(meta);
	}
	
	public static ItemStack checkHasItem(Inventory inventory, ItemStack target) {
		return checkHasItem(inventory.getStorageContents(), target);
	}
	
	public static ItemStack checkHasItem(Player inventory, ItemStack target) {
		return checkHasItem(inventory.getInventory(), target);
	}

	public static ItemStack checkHasItem(ItemStack[] inventory, ItemStack target) {
		for (ItemStack item : inventory) {
			if (item == null || item.getType() == Material.AIR) continue;
			if (item.isSimilar(target) && item.getAmount() >= target.getAmount()) {
				return item;
			}
		}
		return null;
	}
	
	public static void damageItem(ItemStack item) {
		if (item.getItemMeta() instanceof Damageable) {
			Damageable damagemeta = (Damageable) item.getItemMeta();
			if (item.getItemMeta().hasEnchant(Enchantment.DURABILITY)) {
				int enchant = item.getItemMeta().getEnchantLevel(Enchantment.DURABILITY);
				if (random.nextInt(enchant) == 0) {
					damagemeta.setDamage(damagemeta.getDamage()+1);
				}
			} else {
				damagemeta.setDamage(damagemeta.getDamage()+1);
			}
			//damagemeta.setDamage((int) (damagemeta.getDamage()+damage));
			if (damagemeta.getDamage() >= item.getType().getMaxDurability()) {
				item.setAmount(0);
			} else item.setItemMeta((ItemMeta) damagemeta);
		}
	}
	
	public static class Builder {
		String displayName;
		List<String> lore = new ArrayList<>();
		Material material = Material.STONE;
		int amount = 1;
		
		@Deprecated
		public Builder() {
		}
		
		public static Builder builder() {
			return new Builder();
		}
		public ItemStack build() {
			ItemStack item = new ItemStack(material);
			item.setAmount(amount);
			ItemMeta meta = item.getItemMeta();
			meta.setLore(lore);
			if (displayName !=null)meta.setDisplayName(displayName);
			item.setItemMeta(meta);
			return item;
		}
		public Builder setAmount(int amount) {
			this.amount = amount;
			return this;
		}
		public Builder setMaterial(Material material) {
			this.material = material;
			return this;
		}
		public Builder setDisplayName(String displayName) {
			this.displayName = displayName;
			return this;
		}
		public Builder addLore(String...lore) {
			if (lore[0] == null) return this;
			this.lore.addAll(List.of(lore));
			return this;
		}
		public Builder setLore(String...lore) {
			this.lore = new ArrayList<>();
			this.lore.addAll(Arrays.asList(lore));
			return this;
		}
		public Builder setLore(List<String> lore) {
			this.lore = lore;
			return this;
		}
		public Builder clearLore() {
			this.lore = new ArrayList<>();
			return this;
		}
		
	}

}
