package lodestone;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.mask.Mask2D;
import org.ipvp.canvas.paginate.PaginatedMenuBuilder;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.slot.SlotSettings;
import org.ipvp.canvas.template.ItemStackTemplate;
import org.ipvp.canvas.type.ChestMenu;

import java.util.Arrays;

@Singleton
public class LodestoneInterface {

    private Menu lodestone;

    @Inject
    private LodestoneWorld world;

    @Inject
    public LodestoneManager lodestoneManager;

    public void setup() {
        // create menu builder
        Menu.Builder menuBuilder = ChestMenu.builder(6).title("Lodestone").redraw(true);

        // create item layout
        Mask2D itemSlots = (Mask2D) Mask2D.builder(menuBuilder.getDimensions())
                .pattern("000000000")
                .pattern("010101010")
                .pattern("001010100")
                .pattern("010101010")
                .pattern("001010100")
                .pattern("000010000")
                .build();

        // create mask layout
        Mask mask = BinaryMask.builder(menuBuilder.getDimensions())
                .item(new ItemStack(Material.BLACK_STAINED_GLASS_PANE))
                .pattern("111111111")
                .pattern("100000001")
                .pattern("100000001")
                .pattern("100000001")
                .pattern("100000001")
                .pattern("111101111")
                .build();

        // create page builder
        PaginatedMenuBuilder builder = PaginatedMenuBuilder.builder(menuBuilder).slots(itemSlots);


        // loop through all lodestones
        for (LodestoneConfiguration lodestone : lodestoneManager.getLodestones()) {
            builder.addItem(SlotSettings.builder().clickHandler((player, clickInformation) -> {
                        // deal with clicking
                        if (clickInformation.getAction().equals(InventoryAction.PICKUP_ALL)) {
                            LodestonePlayer lodestonePlayer = world.getFromPlayer(player);

                            boolean unlocked = lodestonePlayer.getLodestones().get(lodestone.getId());

                            if (unlocked) {
                                player.teleport(lodestone.getDestination().getBukkitLocation());
                                player.sendMessage("The lodestone teleports you to " + lodestone.getName()) ;
                            } else {
                                player.sendMessage("You have not activated this lodestone.");
                            }
                        }

                    }).itemTemplate(player -> {
                        // set updating template
                        LodestonePlayer lodestonePlayer = world.getFromPlayer(player);

                        boolean unlocked = lodestonePlayer.getLodestones().get(lodestone.getId());

                        return unlocked ? lodestone.getUnlockedItem() : lodestone.getLockedItem();

                    }).build());
        }

        lodestone = builder.build().get(0);

        mask.apply(lodestone);

        // add lecturn to the interface, default item
        lodestone.getSlot(6,5).setItem(createLectern());
    }

    public void open(Player player) {
        lodestone.open(player);

    }


    private ItemStack createLectern() {
        ItemStack stack = new ItemStack( Material.LECTERN);
        ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.LECTERN);
        meta.setDisplayName(ChatColor.GRAY + "Select your teleport destination");
        stack.setItemMeta(meta);
        return stack;
    }

}
