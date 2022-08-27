package info.ahaha.scoreboardplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;

public class Board {
    Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    Objective objective;
    Map<Integer, info.ahaha.scoreboardplugin.Line> lines = new HashMap<>();
    int counter = 0;

    public static String getEntryCode(int num) {
        String ret = "";

        do {
            ret = ret + ChatColor.getByChar(String.valueOf(num % 10));
            num /= 10;
        } while(num != 0);

        return ret;
    }

    public Board(String Name) {
        this.objective = this.scoreboard.getObjective(Name) == null ? this.scoreboard.registerNewObjective(Name, "dummy",Name) : this.scoreboard.getObjective(Name);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void setDisplayName(String str) {
        this.objective.setDisplayName(str);
    }

    public void addLine(info.ahaha.scoreboardplugin.Line line, int row) {
        Score score = this.objective.getScore(line.getEntryCode());
        score.setScore(row);
        this.lines.put(row, line);
    }

    public Board.Line getLine() {
        ++this.counter;
        return new Board.Line();
    }

    /** @deprecated */
    @Deprecated
    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    /** @deprecated */
    @Deprecated
    public Objective getObjective() {
        return this.objective;
    }

    public void show(Player player) {
        player.setScoreboard(this.scoreboard);
    }

    public class Line implements info.ahaha.scoreboardplugin.Line {
        String EntryCode;
        Team team;

        Line() {
            this.EntryCode = Board.getEntryCode(Board.this.counter);
            this.team = Board.this.scoreboard.getTeam(this.EntryCode) == null ? Board.this.scoreboard.registerNewTeam(this.EntryCode) : Board.this.scoreboard.getTeam(this.EntryCode);
            this.team.addEntry(this.EntryCode);
            this.team.setPrefix("");
            this.team.setSuffix("");
        }

        public String getEntryCode() {
            return this.EntryCode;
        }

        public info.ahaha.scoreboardplugin.Line setLine(String str) {
            this.team.setPrefix(str);
            return null;
        }

        public void remove() {
            Board.this.scoreboard.resetScores(this.EntryCode);
        }

        /** @deprecated */
        @Deprecated
        public Team getTeam() {
            return this.team;
        }
    }
}
