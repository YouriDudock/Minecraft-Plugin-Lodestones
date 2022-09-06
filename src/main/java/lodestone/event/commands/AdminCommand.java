package lodestone.event.commands;

import com.google.inject.Inject;
import lodestone.LodestonePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import project.GameCommand;

@GameCommand
public class AdminCommand implements CommandExecutor {

    @Inject
    public LodestonePlugin plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        switch (strings[0].toLowerCase()) {
            case "reload":
                plugin.reloadConfig();

                commandSender.sendMessage("The config has been reloaded.");
                break;
        }

        return true;
    }
}
