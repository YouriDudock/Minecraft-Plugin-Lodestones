package lodestone.event;

import com.google.inject.Inject;
import lodestone.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import storage.JSONStorage;

public class OnPlayerLogEvent implements Listener {

    @Inject
    public LodestoneWorld world;

    @Inject
    public LodestoneManager lodestoneManager;


    @Inject
    public JSONStorage jsonStorage;

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        LodestonePlayer lodestonePlayer = world.getFromPlayer(event.getPlayer());

        if (lodestonePlayer != null) {
            // save lodestone player
            jsonStorage.store(lodestonePlayer);

            world.removePlayer(lodestonePlayer);
        }

    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        // fetch joined player
        Player player = event.getPlayer();

        // fetch mmo player from storage
        LodestonePlayer lodestonePlayer = jsonStorage.load(player.getUniqueId(), LodestonePlayer.class);

        // create new player, because player not in our storage
        if (lodestonePlayer == null) {
            lodestonePlayer = new LodestonePlayer();
            lodestonePlayer.setup();
        }

        // add missing lodestones to the list
        for (LodestoneConfiguration lodestone : lodestoneManager.getLodestones()) {
            lodestonePlayer.getLodestones().putIfAbsent(lodestone.getId(), lodestone.isUnlockedByDefault());
        }

        // set inner player
        lodestonePlayer.setPlayer(player);

        // add to world
        world.addPlayer(lodestonePlayer);
    }
}
