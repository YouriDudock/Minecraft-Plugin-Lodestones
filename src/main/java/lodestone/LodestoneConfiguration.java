package lodestone;

import com.google.gson.annotations.Expose;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import project.generic.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LodestoneConfiguration {

    @Expose
    private int id;

    @Expose
    private String name;

    @Expose
    private String description;

    @Expose
    private Location destination;

    @Expose
    private Location location;

    @Expose
    private List<String> tips;

    private ItemStack item;

    private ItemMeta locked, unlocked;

    @Expose
    private Material display;

    @Expose
    private boolean isUnlockedByDefault;

    public void setup() {
        // create stack with meta
        item = new ItemStack(display);

        locked = Bukkit.getItemFactory().getItemMeta(display);
        locked.setDisplayName(ChatColor.RED + (ChatColor.BOLD + "") + name);
        locked.setLore(Arrays.asList("", ChatColor.GRAY + description));

        unlocked = Bukkit.getItemFactory().getItemMeta(display);
        unlocked.setDisplayName(ChatColor.GREEN + (ChatColor.BOLD + "") + name);

        List<String> unlockedLore = new ArrayList<>();

        // empty line
        unlockedLore.add("");

        unlockedLore.add(ChatColor.GRAY+ "This lodestone has been activated");
        unlockedLore.add("");

        unlockedLore.add(ChatColor.GOLD + (ChatColor.UNDERLINE + "") + "Noteable Activities:");

        for (String tip : tips) {
            unlockedLore.add(ChatColor.GRAY + "- " + tip);
        }

        unlocked.setLore(unlockedLore);
    }


    public Location getDestination() {
        return destination;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ItemStack getUnlockedItem() {
        item.setItemMeta(unlocked);
        return item;
    }

    public ItemStack getLockedItem() {
        item.setItemMeta(locked);
        return item;
    }


    public boolean isUnlockedByDefault() {
        return isUnlockedByDefault;
    }

    public Location getLocation() {
        return location;
    }

    public int getId() {
        return id;
    }

    public List<String> getTips() {
        return tips;
    }
}
