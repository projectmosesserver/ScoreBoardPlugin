package info.ahaha.scoreboardplugin;

import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;

public class BoardData {
    private Scoreboard scoreboard;
    private Objective objective;
    private Map<Integer,String>lines;
    public static BoardData data;

    public BoardData(Scoreboard scoreboard, Objective objective,Map<Integer,String>lines){
        this.scoreboard = scoreboard;
        this.objective = objective;
        this.lines = lines;
        data = this;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Objective getObjective() {
        return objective;
    }

    public Map<Integer, String> getLines() {
        return lines;
    }
}
