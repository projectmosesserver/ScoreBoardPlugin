package info.ahaha.scoreboardplugin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import info.ahaha.scoreboardplugin.model.PlayerData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayersData {
    private List<PlayerData> playerDataList;
    private final ObjectMapper mapper;
    private final File jsonFile;

    public PlayersData(File parent) {
        playerDataList = new ArrayList<>();
        mapper = new ObjectMapper();
        jsonFile = new File(parent, "players.json");
    }

    public void load() {
        try {
            if (jsonFile.createNewFile()) {
                save();
            }
            playerDataList = mapper.readValue(jsonFile, new TypeReference<List<PlayerData>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(jsonFile, playerDataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(UUID uuid, boolean enabled) {
        playerDataList.removeIf(data -> data.getUuid().equals(uuid));

        PlayerData newPlayerData = new PlayerData(uuid, enabled);
        playerDataList.add(newPlayerData);

        CompletableFuture.runAsync(this::save);
    }

    public boolean isEnabled(UUID uuid) {
        Optional<PlayerData> playerData = playerDataList.stream().filter(data -> data.getUuid().equals(uuid)).findAny();
        return !playerData.isPresent() || playerData.get().isEnabled();
    }
}
