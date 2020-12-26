package io.github.eikefs.minecraft.lib.api.parser;

import io.github.eikefs.minecraft.lib.api.parser.impl.ConfigTextParser;

import java.util.HashMap;
import java.util.Map;

public final class Parsers {

    private static final Map<Class<?>, ConfigParser<?>> parsers = new HashMap<>();

    static {
        parsers.put(String.class, new ConfigTextParser());
    }

    private Parsers() {}

    public static <T> ConfigParser<T> of(Class<T> clazz) {
        return (ConfigParser<T>) parsers.get(clazz);
    }

    public static <T> void add(Class<T> clazz, ConfigParser<T> parser) {
        parsers.put(clazz, parser);
    }

}
