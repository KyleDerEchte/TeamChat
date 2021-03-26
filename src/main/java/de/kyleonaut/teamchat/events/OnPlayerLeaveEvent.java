package de.kyleonaut.teamchat.events;

import de.kyleonaut.teamchat.Teamchat;
import de.kyleonaut.teamchat.api.chat.BroadCaster;
import de.kyleonaut.teamchat.api.user.UserManager;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class OnPlayerLeaveEvent implements Listener {
    private final UserManager manager = new UserManager();
    private final BroadCaster caster = new BroadCaster();
    private final Teamchat tc = Teamchat.getInstance();

    @EventHandler
    public void onPlayerLeave(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        manager.removeUser(player.getUniqueId());
        caster.broadcastMessage(tc.getMessage("UserDisconnect")
                .replace("%name%",player.getName()));
    }
}
