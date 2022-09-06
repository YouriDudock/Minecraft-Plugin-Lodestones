package lodestone.binder;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import lodestone.LodestoneInterface;
import lodestone.LodestoneManager;
import lodestone.LodestonePlugin;
import lodestone.LodestoneWorld;
import project.json.JSONConfigManager;
import storage.JSONStorage;

public class PluginBinderModule extends AbstractModule {

    private final LodestonePlugin plugin;

    private final JSONConfigManager configManager;

    private final LodestoneWorld world;

    private final LodestoneInterface lodestoneInterface;

    private final LodestoneManager lodestoneManager;

    private final JSONStorage jsonStorage;


    // This is also dependency injection, but without any libraries/frameworks since we can't use those here yet.
    public PluginBinderModule(LodestonePlugin plugin, JSONConfigManager configManager, JSONStorage jsonStorage, LodestoneWorld world, LodestoneInterface lodestoneInterface, LodestoneManager lodestoneManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.jsonStorage = jsonStorage;
        this.world = world;
        this.lodestoneInterface = lodestoneInterface;
        this.lodestoneManager = lodestoneManager;

    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }

    @Override
    protected void configure() {
        // Here we tell Guice to use our plugin instance everytime we need itd
        this.bind(LodestonePlugin.class).toInstance(this.plugin);
        this.bind(JSONConfigManager.class).toInstance(this.configManager);
        this.bind(JSONStorage.class).toInstance(this.jsonStorage);
        this.bind(LodestoneWorld.class).toInstance(this.world);
        this.bind(LodestoneInterface.class).toInstance(this.lodestoneInterface);
        this.bind(LodestoneManager.class).toInstance(this.lodestoneManager);
    }


}
