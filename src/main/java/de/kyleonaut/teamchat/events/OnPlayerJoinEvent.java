package de.kyleonaut.teamchat.events;

import de.kyleonaut.teamchat.Teamchat;
import de.kyleonaut.teamchat.api.chat.BroadCaster;
import de.kyleonaut.teamchat.api.user.User;
import de.kyleonaut.teamchat.api.user.UserManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class OnPlayerJoinEvent implements Listener {
    private final Teamchat tc = Teamchat.getInstance();
    private final UserManager manager = new UserManager();
    private final BroadCaster caster = new BroadCaster();

    @EventHandler
    public void onPlayerJoin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (!player.hasPermission("teamchat")) {
            return;
        }
        final User user = new User(
                player.getUniqueId(),
                player.getName()
        );
        manager.addUser(user);
        caster.broadcastMessage(tc.getMessage("UserJoin")
                .replace("%name%",player.getName()));
    }
}
