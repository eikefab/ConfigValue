package io.github.eikefs.minecraft.lib.api.parser.impl;

import io.github.eikefs.minecraft.lib.api.parser.ConfigParser;
import org.bukkit.ChatColor;

public class ConfigTextParser implements ConfigParser<String> {


    @Override
    public String serialize(String value) {
        return value;
    }

    @Override
    public String deserialize(String value) {
        return ChatColor.translateAlternateColorCodes('&', value);
    }

}
