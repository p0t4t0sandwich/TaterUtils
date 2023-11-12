package dev.neuralnexus.taterutils.common.modules.home.command;

import dev.neuralnexus.taterlib.common.command.Command;
import dev.neuralnexus.taterlib.common.command.Sender;
import dev.neuralnexus.taterlib.common.player.Player;
import dev.neuralnexus.taterutils.common.api.TaterUtilsAPIProvider;
import dev.neuralnexus.taterutils.common.modules.home.api.HomeAPI;
import dev.neuralnexus.taterutils.common.api.CommandUtils;

/**
 * SetHome Command.
 */
public class SetHomeCommand implements Command {
    private String name = "sethome";

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "Allows players to set a home.";
    }

    @Override
    public String getUsage() {
        return "/sethome <name>";
    }

    @Override
    public String getPermission() {
        return "taterutils.command.sethome";
    }

    @Override
    public String execute(String[] args) {
        return null;
    }

    @Override
    public boolean execute(Sender sender, String label, String[] args) {
        if (!CommandUtils.senderIsPlayerAndHasPermission(sender, getPermission())) {
            return true;
        }
        Player player = (Player) sender;
        HomeAPI api = TaterUtilsAPIProvider.get().getHomeAPI();

        if (args.length == 0) {
            CommandUtils.sendMessage(player, "&aPlease provide a Home name!");
        } else {
            if (api.getInvalidHomeNames().contains(args[1])) {
                CommandUtils.sendMessage(player, "&cInvalid home name.");
                return true;
            }
            api.setHome(player, args[0], player.getLocation());
            CommandUtils.sendMessage(player, "&aSet home &e" + args[0] + "&a.");
        }
        return true;
    }
}