package com.todayWife;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class WifeManager {
    private final TodayWife plugin;
    private final Map<UUID, Wife> wifeMap;
    private File dataFile;
    private FileConfiguration dataConfig;

    public WifeManager(TodayWife plugin) {
        this.plugin = plugin;
        this.wifeMap = new HashMap<>();
        loadAllData();
    }

    public Wife getWife(Player player) {
        Wife wife = wifeMap.get(player.getUniqueId());
        if (wife != null && wife.isExpired()) {
            wifeMap.remove(player.getUniqueId());
            return null;
        }
        return wife;
    }

    public void setWife(Player player, Wife wife) {
        wifeMap.put(player.getUniqueId(), wife);
        // 播放音效，但是这个坏掉了
        plugin.getSoundManager().playWifeSound(player);
    }

    public void removeWife(Player player) {
        wifeMap.remove(player.getUniqueId());
    }

    public boolean hasWife(Player player) {
        Wife wife = getWife(player);
        return wife != null;
    }

    public void interact(Player player, String interactionType) {
        Wife wife = getWife(player);
        if (wife != null) {
            // 检查互动是否启用
            if (!plugin.getConfigManager().isInteractionEnabled(interactionType)) {
                player.sendMessage(plugin.getConfigManager().getMessage("interaction-disabled"));
                return;
            }

            // 随机增减好感度，可以修改
            int change = (int) (Math.random() * 3) - 1; // -1, 0, 1

            // SM有更大波动，你懂的
            if (interactionType.equals("bondage")) {
                change = (int) (Math.random() * 5) - 2; // -2, -1, 0, 1, 2
            }

            wife.addAffection(change);

            // 发送消息
            String message = plugin.getConfigManager().getMessage("interaction." + interactionType)
                    .replace("{player}", wife.getWifeName())
                    .replace("{change}", (change >= 0 ? "+" : "") + change);
            player.sendMessage(message);

            // 播放互动音效
            plugin.getSoundManager().playInteractionSound(player);
        }
    }

    public void loadAllData() {
        dataFile = new File(plugin.getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "创建数据文件时出错", e);
            }
        }

        dataConfig = YamlConfiguration.loadConfiguration(dataFile);

        if (dataConfig.contains("wives")) {
            for (String key : dataConfig.getConfigurationSection("wives").getKeys(false)) {
                try {
                    UUID playerUUID = UUID.fromString(key);
                    String wifeName = dataConfig.getString("wives." + key + ".name");
                    String uuidString = dataConfig.getString("wives." + key + ".uuid");
                    UUID wifeUUID = uuidString != null ? UUID.fromString(uuidString) : null;
                    int affection = dataConfig.getInt("wives." + key + ".affection");
                    long lastSelected = dataConfig.getLong("wives." + key + ".lastSelected");

                    Wife wife = new Wife(wifeName, wifeUUID, affection, lastSelected);
                    if (!wife.isExpired()) {
                        wifeMap.put(playerUUID, wife);
                    }
                } catch (Exception e) {
                    plugin.getLogger().log(Level.WARNING, "加载玩家 " + key + " 的老婆数据时出错", e);
                }
            }
        }
    }

    public void saveAllData() {
        if (dataConfig == null) {
            return;
        }

        dataConfig.set("wives", null); // 清除旧数据

        for (Map.Entry<UUID, Wife> entry : wifeMap.entrySet()) {
            String path = "wives." + entry.getKey().toString();
            Wife wife = entry.getValue();

            dataConfig.set(path + ".name", wife.getWifeName());
            if (wife.getWifeUUID() != null) {
                dataConfig.set(path + ".uuid", wife.getWifeUUID().toString());
            }
            dataConfig.set(path + ".affection", wife.getAffection());
            dataConfig.set(path + ".lastSelected", wife.getLastSelected());
        }

        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "保存老婆数据时出错", e);
        }
    }
}