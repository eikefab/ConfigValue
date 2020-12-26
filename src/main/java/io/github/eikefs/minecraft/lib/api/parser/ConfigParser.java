package io.github.eikefs.minecraft.lib.api.parser;

public interface ConfigParser<T> {

    String serialize(T value);
    T deserialize(String value);

}
