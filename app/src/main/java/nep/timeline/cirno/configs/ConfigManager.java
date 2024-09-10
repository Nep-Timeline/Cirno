package nep.timeline.cirno.configs;

public class ConfigManager {
    private static final ConfigManagerInterface manager = new ConfigManagerJson();

    public static void readConfig() {
        manager.readConfig();
        manager.saveConfig();
    }

    public static void saveConfig() {
        manager.saveConfig();
        manager.readConfig();
    }
}
