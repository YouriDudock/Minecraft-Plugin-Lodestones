package lodestone;

import com.google.gson.annotations.Expose;
import org.bukkit.entity.Player;
import storage.PlayerStorageContainer;

import java.util.HashMap;
import java.util.UUID;

public class LodestonePlayer implements PlayerStorageContainer {

    /**
     * The inner player of this lodestone player
     */
    private Player player;

    @Expose
    private HashMap<Integer, Boolean> lodestones;

    @Override
    public UUID getUUID() {
        return player.getUniqueId();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public HashMap<Integer, Boolean> getLodestones() {
        return lodestones;
    }

    public void setup() {
        lodestones = new HashMap<>();
    }
}
