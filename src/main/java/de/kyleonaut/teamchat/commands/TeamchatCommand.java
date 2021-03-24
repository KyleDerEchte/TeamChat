package de.kyleonaut.teamchat.commands;

import de.kyleonaut.teamchat.Teamchat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class TeamchatCommand extends Command {

    private final Teamchat plugin;

    public TeamchatCommand(Teamchat plugin) {
        super("Teamchat", "teamchat", "tc");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("teamchat")) {
            sender.sendMessage(plugin.getMessage("NoPermission"));
            return;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("reload") && sender.hasPermission("teamchat.admin")) {
            sender.sendMessage(plugin.getMessage("ConfigReload"));
            return;
        }
        if (!(sender instanceof ProxiedPlayer)) {
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) sender;
        if (plugin.getTeamchatEnabledPlayers().contains(player.getUniqueId())) {
            plugin.getTeamchatEnabledPlayers().remove(player.getUniqueId());
            player.sendMessage(plugin.getMessage("DisabledTeamChat"));
            return;
        }
        plugin.getTeamchatEnabledPlayers().add(player.getUniqueId());
        player.sendMessage(plugin.getMessage("EnabledTeamChat"));
    }
}
