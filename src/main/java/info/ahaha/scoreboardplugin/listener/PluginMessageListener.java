package info.ahaha.scoreboardplugin.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import info.ahaha.scoreboardplugin.ScoreBoardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
        if (!s.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subchannel = in.readUTF();
        if (subchannel.equalsIgnoreCase("PlayerCount")) {
            try {
                String server = in.readUTF();
                int num = in.readInt();
                ScoreBoardPlugin.plugin.putPlayerCount(server, num);
            } catch (Exception ignored) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "プレイヤー数を取得する際に例外が発生しました。ConfigにてServersの項目に開いていないサーバーがある可能性があります。");
            }
        }
    }
}
