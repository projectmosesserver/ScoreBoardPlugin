package info.ahaha.scoreboardplugin.listener;

import info.ahaha.scoreboardplugin.ScoreBoardPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        ScoreBoardPlugin.ScoreBoards scoreBoards = new ScoreBoardPlugin.ScoreBoards(e.getPlayer());
        scoreBoards.show(e.getPlayer());
    }
}
