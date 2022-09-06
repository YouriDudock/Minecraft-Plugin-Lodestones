package lodestone.event;

import com.google.inject.Inject;
import lodestone.LodestoneManager;
import lodestone.LodestonePlugin;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractBlock implements Listener {



    @Inject
    public LodestoneManager lodestoneManager;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            // check if player hits a lodestone
            if (event.getClickedBlock().getType() == LodestonePlugin.LODESTONE_BLOCK) {
                // open the lodestone menu
                lodestoneManager.open(event.getClickedBlock(), event.getPlayer());
            }
        }
    }
}
