package me.hsgamer.simpleprogressbar;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class SimpleProgressBar extends PlaceholderExpansion implements Configurable {
    private final String version = getClass().getPackage().getImplementationVersion();

    @Override
    public Map<String, Object> getDefaults() {
        Map<String, Object> map = new HashMap<>();
        map.put("length", 10);
        map.put("finished-char", "|");
        map.put("not-finished-char", "|");
        map.put("finished-color", "a");
        map.put("not-finished-color", "7");
        return map;
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String params) {
        if (offlinePlayer == null) {
            return "";
        }

        params = PlaceholderAPI.setPlaceholders(offlinePlayer, params.replaceAll("\\$\\((.*?)\\)\\$", "%$1%"));
        params = PlaceholderAPI.setBracketPlaceholders(offlinePlayer, params);

        String finishedChar = getString("finished-char", "|");
        String notFinishedChar = getString("not-finished-char", "|");
        ChatColor finishedColor = ChatColor.getByChar(getString("finished-color", "a"));
        ChatColor notFinishedColor = ChatColor.getByChar(getString("not-finished-color", "7"));
        int length = getInt("length", 10);

        double current = 0;
        double full = 100;

        try {
            String[] args = params.split(";", 2);
            if (args.length > 0) {
                current = Double.parseDouble(args[0].trim());
            }
            if (args.length > 1) {
                full = Double.parseDouble(args[1].trim());
            }
        } catch (Exception e) {
            // IGNORED
        }

        int finished = (int) Math.floor((current / full) * length);

        StringBuilder builder = new StringBuilder();

        builder.append(finishedColor);
        for (int i = 0; i < finished; i++) {
            builder.append(finishedChar);
        }

        builder.append(notFinishedColor);
        for (int i = finished; i < length; i++) {
            builder.append(notFinishedChar);
        }

        return builder.toString();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "simplebar";
    }

    @Override
    public @NotNull String getAuthor() {
        return "HSGamer";
    }

    @Override
    public @NotNull String getVersion() {
        return version;
    }
}
