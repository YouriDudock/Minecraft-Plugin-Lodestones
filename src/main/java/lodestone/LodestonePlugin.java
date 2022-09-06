package lodestone;

import com.google.inject.Inject;
import lodestone.binder.PluginBinderModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.ipvp.canvas.MenuFunctionListener;
import project.ProjectManager;
import project.YPlugin;
import project.json.JSONConfigManager;
import storage.JSONStorage;

public class LodestonePlugin extends YPlugin {

    public static Material LODESTONE_BLOCK = Material.LECTERN;

    public ProjectManager project;

    @Inject
    public LodestoneInterface lodestoneInterface;

    @Inject
    public LodestoneManager lodestoneManager;

    @Inject
    public JSONStorage jsonStorage;

    @Inject
    public LodestoneWorld world;

    public LodestonePlugin() {

    }

    @Override
    public void onEnable() {
        setup("lodestone",
                new PluginBinderModule(this,
                        new JSONConfigManager(this),
                        new JSONStorage(), new LodestoneWorld(),
                        new LodestoneInterface(), new LodestoneManager()
                )
        );

        reloadConfig();

        reloadWorld();

        Bukkit.getPluginManager().registerEvents(new MenuFunctionListener(), this);

        lodestoneInterface.setup();
    }


    @Override
    public void reloadConfig() {
        lodestoneManager.loadConfig();
        lodestoneInterface.setup();
    }



    private void reloadWorld() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            LodestonePlayer lodestonePlayer = jsonStorage.load(player.getUniqueId(), LodestonePlayer.class);

            lodestonePlayer.setPlayer(player);

            world.addPlayer(lodestonePlayer);
        }
    }

    @Override
    protected void loadInjection() {
        project.injectModuleInstance(LodestoneInterface.class);
        project.injectModuleInstance(LodestoneManager.class);
    }

}
