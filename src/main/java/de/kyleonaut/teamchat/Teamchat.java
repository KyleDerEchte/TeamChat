package de.kyleonaut.teamchat;

import de.kyleonaut.teamchat.api.user.User;
import de.kyleonaut.teamchat.commands.TeamchatCommand;
import de.kyleonaut.teamchat.events.OnMessageEvent;
import de.kyleonaut.teamchat.events.OnPlayerJoinEvent;
import de.kyleonaut.teamchat.events.OnPlayerLeaveEvent;
import lombok.Getter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public final class Teamchat extends Plugin {

    private Configuration config;
    private String prefix;
    private final List<UUID> teamchatEnabledPlayers = new ArrayList<>();
    private final List<User> onlineTeamUsers = new ArrayList<>();
    private static Teamchat instance;

    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;
        File theDir = new File(ProxyServer.getInstance().getPluginsFolder().getPath() + "/Teamchat");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.getConfigPath().toFile());
        prefix = this.config.getString("prefix.Prefix");
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new TeamchatCommand(this));
        ProxyServer.getInstance().getPluginManager().registerListener(this, new OnMessageEvent());
        ProxyServer.getInstance().getPluginManager().registerListener(this,new OnPlayerJoinEvent());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new OnPlayerLeaveEvent());
    }

    @SneakyThrows
    public Path getConfigPath() {
        final Path path = Paths.get("plugins", "Teamchat", "config.yml");
        if (Files.notExists(path)) {
            final InputStream in = Teamchat.class.getClassLoader().getResourceAsStream("config.yml");
            if (in == null) {
                throw new NullPointerException("Resource not found!");
            }
            Files.copy(in, path);
        }
        return path;
    }

    public String getMessage(String path) {
        return ChatColor.translateAlternateColorCodes('&', this.config.getString("messages." + path)
                .replace("%prefix%", prefix));
    }

    public ProxiedPlayer getPlayerByUUID(UUID uuid){
        return ProxyServer.getInstance().getPlayer(uuid);
    }

    public static Teamchat getInstance(){
        return instance;
    }
}
