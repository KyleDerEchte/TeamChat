package de.kyleonaut.teamchat.events;

import de.kyleonaut.teamchat.Teamchat;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class OnMessageEvent implements Listener {

    private final Teamchat plugin = Teamchat.getInstance();

    @EventHandler
    public void onMessage(ChatEvent event) {
        if (event.getMessage().startsWith("/")) return;
        for (ProxiedPlayer proxiedPlayer : ProxyServer.getInstance().getPlayers()) {
            if (proxiedPlayer.getSocketAddress().equals(event.getSender().getSocketAddress()) &&
                    plugin.getTeamchatEnabledPlayers().contains(proxiedPlayer.getUniqueId())) {
                event.setCancelled(true);
                plugin.getTeamchatEnabledPlayers().forEach(uuid -> {
                    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
                    player.sendMessage(getChatMessage(player, event));
                });
                break;
            }
        }
    }

    private BaseComponent getChatMessage(ProxiedPlayer proxiedPlayer, ChatEvent event) {
        final BaseComponent chatmessage = new TextComponent(plugin.getMessage("Format")
                .replace("%name%", proxiedPlayer.getDisplayName())
                .replace("%message%", event.getMessage())
                .replace('&', '§'));
        chatmessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new Text(plugin.getMessage("HoverEventMessage")
                        .replace("%server%", proxiedPlayer.getServer().getInfo().getName()))));
        return chatmessage;
    }
}
