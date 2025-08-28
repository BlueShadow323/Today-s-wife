package com.todayWife;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ConfigManager {
    private TodayWife plugin;
    private FileConfiguration config;
    private FileConfiguration languageConfig;
    private String currentLanguage;

    public ConfigManager(TodayWife plugin) {
        this.plugin = plugin;
        loadConfigs();
    }

    public void loadConfigs() {
        // 保存默认配置
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        config = plugin.getConfig();

        // 加载语言文件
        loadLanguageFile();
    }

    private void loadLanguageFile() {
        String language = config.getString("language", "zh_CN");
        currentLanguage = language;

        File languageFile = new File(plugin.getDataFolder() + "/languages", language + ".yml");
        if (!languageFile.exists()) {
            plugin.saveResource("languages/" + language + ".yml", false);
        }

        languageConfig = YamlConfiguration.loadConfiguration(languageFile);

        // 加载默认语言文件作为后备
        InputStream defaultStream = plugin.getResource("languages/zh_CN.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream, StandardCharsets.UTF_8));
            languageConfig.setDefaults(defaultConfig);
        }
    }

    public String getMessage(String path) {
        return languageConfig.getString(path, "§c消息未找到: " + path).replace('&', '§');
    }

    public List<String> getMessageList(String path) {
        return languageConfig.getStringList(path).stream()
                .map(line -> line.replace('&', '§'))
                .collect(java.util.stream.Collectors.toList());
    }

    public boolean isInteractionEnabled(String interaction) {
        return config.getBoolean("interactions." + interaction, true);
    }

    public boolean isPluginEnabled() {
        return config.getBoolean("enabled", true);
    }

    public void reloadConfigs() {
        plugin.reloadConfig();
        config = plugin.getConfig();
        loadLanguageFile();
    }

    public String getCurrentLanguage() {
        return currentLanguage;
    }
}