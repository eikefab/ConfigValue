# ConfigValue
A library which turns configuration reading easy.

# How to use

Your field must be `static`.

Reading from the default file (config.yml):

```java
public class A {
  
  @ConfigValue("config-version") private static int configVersion; // Once you map it, that's gonna be the config-version value.
  
}
```

```java
@ConfigFile("my-config-file.yml")
public class B {

  @ConfigValue("my-message") private static String message;
  @ConfigValue("my-second-message") private static String secondMessage;

  @ConfigValue(file = "config.yml", value = "config-version") private static int configVersion;

}
```

You must to map them, so add this on your `JavaPlugin#onEnable()`:

```java
ConfigMapper.map(getDataFolder(), "your.package.here");
```

