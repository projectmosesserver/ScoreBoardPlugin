package info.ahaha.scoreboardplugin;

import org.bukkit.scoreboard.Team;

public interface Line {
    String getEntryCode();

    Line setLine(String var1);

    Team getTeam();
}
