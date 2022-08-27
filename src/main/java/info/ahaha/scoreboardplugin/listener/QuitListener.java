package info.ahaha.scoreboardplugin.listener;

import info.ahaha.scoreboardplugin.ScoreBoardPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        ScoreBoardPlugin.boards.remove(e.getPlayer().getUniqueId());
    }
}
