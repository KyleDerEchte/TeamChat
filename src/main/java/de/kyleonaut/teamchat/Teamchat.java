package de.kyleonaut.teamchat;

import de.kyleonaut.teamchat.commands.TeamchatCommand;
import de.kyleonaut.teamchat.events.OnMessageEvent;
import lombok.SneakyThrows;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
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

public final class Teamchat extends Plugin {

    private Configuration config;
    private String prefix;
    private final List<UUID> teamchatEnabledPlayers = new ArrayList<>();

    @SneakyThrows
    @Override
    public void onEnable() {
        File theDir = new File(ProxyServer.getInstance().getPluginsFolder().getPath() + "/Teamchat");
        if (!theDir.exists()) {
            theDir.mkdirs();
        }
        this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.getConfigPath().toFile());
        prefix = this.config.getString("prefix.Prefix");
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new TeamchatCommand(this));
        ProxyServer.getInstance().getPluginManager().registerListener(this, new OnMessageEvent(this));
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

    public List<UUID> getTeamchatEnabledPlayers() {
        return teamchatEnabledPlayers;
    }
}
