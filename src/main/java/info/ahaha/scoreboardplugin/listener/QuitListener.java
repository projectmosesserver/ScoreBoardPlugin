package info.ahaha.scoreboardplugin.listener;

import com.google.common.collect.Iterables;
import info.ahaha.scoreboardplugin.ScoreBoardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        ScoreBoardPlugin.boards.remove(e.getPlayer().getUniqueId());
        FileConfiguration config = ScoreBoardPlugin.plugin.getConfig();
        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(),null);
        config.getStringList("Servers").forEach(s -> {
            ScoreBoardPlugin.plugin.sendPlayerCountMessage(player, s);
        });
    }
}
