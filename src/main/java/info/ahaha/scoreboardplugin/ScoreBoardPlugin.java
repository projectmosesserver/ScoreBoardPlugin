package info.ahaha.scoreboardplugin;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import info.ahaha.scoreboardplugin.commands.Cmd;
import info.ahaha.scoreboardplugin.listener.JoinListener;
import info.ahaha.scoreboardplugin.listener.PluginMessageListener;
import info.ahaha.scoreboardplugin.listener.QuitListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class ScoreBoardPlugin extends JavaPlugin {

    public static ScoreBoardPlugin plugin;
    public static TPSMonitor monitor;
    public static Map<UUID, ScoreBoards> boards = new HashMap<>();
    public static PlayersData playersData;
    private Map<String, Integer> playerCounts = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        saveDefaultConfig();
        monitor = new TPSMonitor();
        monitor.start(this);

        getDataFolder().mkdir();
        playersData = new PlayersData(getDataFolder());
        playersData.load();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessageListener());

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (playersData.isEnabled(player.getUniqueId())) {
                new ScoreBoards(player);
            }
        }

        if (Bukkit.getOnlinePlayers().size() != 0) {
            FileConfiguration config = getConfig();
            Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
            config.getStringList("Servers").forEach(s -> {
                sendPlayerCountMessage(player, s);
            });
        }

        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new PlayerSize(), 0, 20);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new TPS(), 0, 20);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Ping(), 0, 20);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Memory(), 0, 20);

        getCommand("sb").setExecutor(new Cmd());
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void sendPlayerCountMessage(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(server);
        player.sendPluginMessage(this, "BungeeCord", out.toByteArray());
    }

    public void putPlayerCount(String server, int num) {
        playerCounts.put(server, num);
    }

    public static class ScoreBoards {
        public ScoreBoards(Player player) {
            boards.put(player.getUniqueId(), this);
            String name = player.getName();
            int psize = 0;
            for (int i : plugin.playerCounts.values()) {
                psize += i;
            }
            String tps = String.valueOf(Math.round(monitor.getTPS() * 100.0) / 100.0);
            double max = Runtime.getRuntime().maxMemory() / (double) (1024 * 1024);
            double used = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (double) (1024 * 1024);
            int ping = player.getPing();
            line1 = board.getLine();
            board.addLine(line1, 12);
            this.name = board.getLine();
            board.addLine(this.name, 11);
            this.name1 = board.getLine();
            board.addLine(this.name1, 10);
            this.size = board.getLine();
            board.addLine(this.size, 9);
            this.size1 = board.getLine();
            board.addLine(this.size1, 8);
            this.ping = board.getLine();
            board.addLine(this.ping, 7);
            this.ping1 = board.getLine();
            board.addLine(ping1, 6);
            this.tps = board.getLine();
            board.addLine(this.tps, 5);
            this.tps1 = board.getLine();
            board.addLine(this.tps1, 4);
            this.memory = board.getLine();
            board.addLine(this.memory, 3);
            this.memory1 = board.getLine();
            board.addLine(this.memory1, 2);
            this.line2 = board.getLine();
            board.addLine(this.line2, 1);

            line1.setLine(ChatColor.GRAY + "+----------------------+");
            this.name.setLine(ChatColor.YELLOW + "MCID " + ChatColor.GRAY + ">> ");
            this.name1.setLine(ChatColor.AQUA + "          " + name);
            this.size.setLine(ChatColor.YELLOW + "Online " + ChatColor.GRAY + ">> ");
            this.size1.setLine(ChatColor.AQUA + "          " + psize);
            this.ping.setLine(ChatColor.YELLOW + "Ping " + ChatColor.GRAY + ">> ");
            this.ping1.setLine(ChatColor.AQUA + "          " + ping);
            this.tps.setLine(ChatColor.YELLOW + "TPS " + ChatColor.GRAY + ">> ");
            this.tps1.setLine(ChatColor.AQUA + "          " + tps);
            this.memory.setLine(ChatColor.YELLOW + "Memory " + ChatColor.GRAY + ">> ");
            this.memory1.setLine(ChatColor.AQUA + "          " + Math.round(used * 100.0) / 100.0 + " / " + Math.round(max * 100.0) / 100.0 + " mb");
            this.line2.setLine(ChatColor.GRAY + "+----------------------+");

            board.show(player);
        }

        public void show(Player player) {
            board.show(player);
        }

        public Board board = new Board(ChatColor.GOLD + "" + ChatColor.BOLD + "ProjectMoses");
        public Board.Line line1;
        public Board.Line line2;
        public Board.Line size;
        public Board.Line size1;
        public Board.Line name;
        public Board.Line name1;
        public Board.Line ping;
        public Board.Line ping1;
        public Board.Line tps;
        public Board.Line tps1;
        public Board.Line memory;
        public Board.Line memory1;
    }

    private static class Ping implements Runnable {

        @Override
        public void run() {
            for (Player player : Bukkit.getOnlinePlayers()) {
                int ping = player.getPing();
                if (boards.get(player.getUniqueId()) == null) continue;
                boards.get(player.getUniqueId()).ping1.setLine(ChatColor.AQUA + "          " + ping);
            }
        }
    }

    private static class PlayerSize implements Runnable {

        @Override
        public void run() {
            if (Bukkit.getOnlinePlayers().size() == 0) return;
            Player fp = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
            plugin.getConfig().getStringList("Servers").forEach(s -> plugin.sendPlayerCountMessage(fp, s));
            int psize = 0;
            for (int i : plugin.playerCounts.values()) {
                psize += i;
            }
            if (psize == 0) return;
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (boards.get(player.getUniqueId()) == null) continue;
                boards.get(player.getUniqueId()).size1.setLine(ChatColor.AQUA + "          " + psize);
            }
        }
    }

    private static class TPS implements Runnable {

        @Override
        public void run() {
            String tps = String.valueOf(Math.round(monitor.getTPS() * 100.0) / 100.0);
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (boards.get(player.getUniqueId()) == null) continue;
                boards.get(player.getUniqueId()).tps1.setLine(ChatColor.AQUA + "          " + tps);
            }
        }
    }

    private static class Memory implements Runnable {

        @Override
        public void run() {
            double max = Runtime.getRuntime().maxMemory() / (double) (1024 * 1024);
            double used = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (double) (1024 * 1024);
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (boards.get(player.getUniqueId()) == null) continue;
                boards.get(player.getUniqueId()).memory1.setLine(ChatColor.AQUA + "          " + Math.round(used * 100.0) / 100.0 + " / " + Math.round(max * 100.0) / 100.0 + " mb");
            }
        }
    }

    public static Map<UUID, ScoreBoards> getBoards() {
        return boards;
    }
}
