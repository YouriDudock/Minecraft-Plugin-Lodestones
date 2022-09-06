package lodestone;

import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import project.json.JSONConfigManager;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class LodestoneManager {

    @Inject
    public LodestonePlugin plugin;

    @Inject
    public LodestoneInterface lodestoneInterface;

    @Inject
    public JSONConfigManager configManager;

    @Inject
    public LodestoneWorld world;

    private List<LodestoneConfiguration> lodestones;

    public void loadConfig() {
        plugin.getLogger().info("Loading config from file..");

        if (lodestones != null) {
            lodestones.clear();
        }

        // load from config
        lodestones = configManager.fromConfig("lodestones.json", new TypeToken<List<LodestoneConfiguration>>(){}.getType());

        lodestones.forEach(LodestoneConfiguration::setup);

        plugin.getLogger().info("Loaded lodestones " + lodestones.size());
    }

    public List<LodestoneConfiguration> getLodestones() {
        return lodestones;
    }

    public void open(Block clickedBlock, Player player) {
        for (LodestoneConfiguration lodestone : lodestones) {

            // check if lodestone is unlocked by default, because then we don't need to do anything
            if (lodestone.isUnlockedByDefault()) {
                continue;
            }

            // check if location match
            if (clickedBlock.getLocation().equals(lodestone.getLocation().getBukkitLocation())) {
                LodestonePlayer lodestonePlayer = world.getFromPlayer(player);

                if (lodestonePlayer != null) {
                    // get lock status
                    boolean unlocked = lodestonePlayer.getLodestones().get(lodestone.getId());

                    // check if player has unlocked the lodestone
                    if (!unlocked) {
                        // not unlocked so lets unlock it now
                        lodestonePlayer.getLodestones().replace(lodestone.getId(), true);

                        player.sendMessage(ChatColor.GREEN + "You have activated the lodestone: " + lodestone.getName());
                    }
                }
            }
        }

        lodestoneInterface.open(player);
    }
}
