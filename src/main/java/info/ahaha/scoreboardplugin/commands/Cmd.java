package info.ahaha.scoreboardplugin.commands;

import info.ahaha.scoreboardplugin.ScoreBoardPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class Cmd implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (ScoreBoardPlugin.boards.containsKey(player.getUniqueId())){
                ScoreBoardPlugin.boards.remove(player.getUniqueId());
                player.setScoreboard(getServer().getScoreboardManager().getNewScoreboard());
                ScoreBoardPlugin.playersData.update(player.getUniqueId(), false);
            }else {
                ScoreBoardPlugin.boards.put(player.getUniqueId(), new ScoreBoardPlugin.ScoreBoards(player));
                ScoreBoardPlugin.playersData.update(player.getUniqueId(), true);
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
