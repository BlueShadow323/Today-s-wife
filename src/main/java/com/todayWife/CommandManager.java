package com.todayWife;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandManager implements TabExecutor {
    private TodayWife plugin;

    public CommandManager(TodayWife plugin) {
        this.plugin = plugin;
    }

    public void registerCommands() {
        plugin.getCommand("todaywife").setExecutor(this);
        plugin.getCommand("todaywife").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c只有玩家可以使用此命令!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            showHelp(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                handleStart(player);
                break;

            case "hate":
                handleHate(player);
                break;

            case "kiss":
            case "molest":
            case "hug":
            case "bondage":
            case "caress":
                handleInteraction(player, args[0].toLowerCase());
                break;

            case "info":
                handleInfo(player);
                break;

            case "reload":
                handleReload(player);
                break;

            default:
                showHelp(player);
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.addAll(Arrays.asList("start", "hate", "kiss", "molest", "hug", "bondage", "caress", "info", "reload"));

            // 过滤匹配的补全选项
            return completions.stream()
                    .filter(c -> c.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }

        return completions;
    }

    private void showHelp(Player player) {
        for (String line : plugin.getConfigManager().getMessageList("help")) {
            player.sendMessage(line);
        }
    }

    private void handleStart(Player player) {
        if (plugin.getWifeManager().hasWife(player)) {
            player.sendMessage(plugin.getConfigManager().getMessage("already-have-wife"));
        } else {
            // 随机选择老婆，可修改
            Wife wife = selectRandomWife(player);
            plugin.getWifeManager().setWife(player, wife);
            player.sendMessage(plugin.getConfigManager().getMessage("wife-selected")
                    .replace("{wife}", wife.getWifeName()));
        }
    }

    private void handleHate(Player player) {
        if (plugin.getWifeManager().hasWife(player)) {
            plugin.getWifeManager().removeWife(player);
            player.sendMessage(plugin.getConfigManager().getMessage("wife-removed"));
        } else {
            player.sendMessage(plugin.getConfigManager().getMessage("no-wife"));
        }
    }

    private void handleInteraction(Player player, String interactionType) {
        if (plugin.getWifeManager().hasWife(player)) {
            plugin.getWifeManager().interact(player, interactionType);
        } else {
            player.sendMessage(plugin.getConfigManager().getMessage("no-wife"));
        }
    }

    private void handleInfo(Player player) {
        if (plugin.getWifeManager().hasWife(player)) {
            Wife wife = plugin.getWifeManager().getWife(player);
            player.sendMessage(plugin.getConfigManager().getMessage("wife-info")
                    .replace("{wife}", wife.getWifeName())
                    .replace("{affection}", String.valueOf(wife.getAffection())));
        } else {
            player.sendMessage(plugin.getConfigManager().getMessage("no-wife"));
        }
    }

    private void handleReload(Player player) {
        if (player.hasPermission("todaywife.reload")) {
            plugin.getConfigManager().reloadConfigs();
            player.sendMessage(plugin.getConfigManager().getMessage("config-reloaded"));
        } else {
            player.sendMessage(plugin.getConfigManager().getMessage("no-permission"));
        }
    }

    private Wife selectRandomWife(Player player) {
        // 从在线玩家中随机选择一个（排除自己，也可以不排除，自交？）
        List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
        onlinePlayers.remove(player);

        if (onlinePlayers.isEmpty()) {
            // 如果没有其他在线玩家，使用默认NPC名称，神秘的少年，也可以是少年?
            return new Wife("神秘少女", 0);
        }

        Player wifePlayer = onlinePlayers.get((int) (Math.random() * onlinePlayers.size()));
        return new Wife(wifePlayer.getName(), wifePlayer.getUniqueId(), 0, System.currentTimeMillis());
    }
}