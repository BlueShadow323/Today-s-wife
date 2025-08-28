package com.todayWife;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class VersionAdapter {
    private static final Map<String, Sound> soundMap = new HashMap<>();
    private static String serverVersion;
    private static boolean isFolia;

    static {
        try {
            // 初始化声音映射，但是有Bug，我懒得修awa
            initializeSoundMap();
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "初始化VersionAdapter时出错", e);
        }
    }

    private static void initializeSoundMap() {
        // 1.9+ 使用新声音系统
        if (isVersionNewerThan("1.9")) {
            try {
                soundMap.put("RANDOM_SUCCESS", Sound.valueOf("ENTITY_PLAYER_LEVELUP"));
                soundMap.put("RANDOM_CHIME", Sound.valueOf("BLOCK_NOTE_BELL"));
            } catch (IllegalArgumentException e) {
                // 备选方案
                try {
                    soundMap.put("RANDOM_SUCCESS", Sound.valueOf("ENTITY_EXPERIENCE_ORB_PICKUP"));
                    soundMap.put("RANDOM_CHIME", Sound.valueOf("BLOCK_NOTE_PLING"));
                } catch (IllegalArgumentException ex) {
                    Bukkit.getLogger().warning("无法加载声音，音效功能将不可用");
                }
            }
        } else {
            // 1.8及以下版本
            try {
                soundMap.put("RANDOM_SUCCESS", Sound.valueOf("LEVEL_UP"));
                soundMap.put("RANDOM_CHIME", Sound.valueOf("NOTE_PLING"));
            } catch (IllegalArgumentException e) {
                Bukkit.getLogger().warning("无法加载声音，音效功能将不可用");
            }
        }
    }

    public static void playSound(Player player, String soundName) {
        Sound sound = soundMap.get(soundName);
        if (sound != null) {
            player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
        }
    }

    public static boolean isVersionNewerThan(String version) {
        try {
            String[] currentParts = serverVersion.split("_");
            String[] targetParts = version.split("\\.");

            int currentMajor = Integer.parseInt(currentParts[1]);
            int targetMajor = Integer.parseInt(targetParts[0]);

            if (currentMajor != targetMajor) {
                return currentMajor > targetMajor;
            }

            int currentMinor = Integer.parseInt(currentParts[2].substring(1));
            int targetMinor = Integer.parseInt(targetParts[1]);

            return currentMinor >= targetMinor;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getServerVersion() {
        return serverVersion;
    }

    public static boolean isFolia() {
        return isFolia;
    }
}