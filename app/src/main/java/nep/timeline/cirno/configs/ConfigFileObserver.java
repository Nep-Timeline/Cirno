package nep.timeline.cirno.configs;

import android.os.FileObserver;
import android.os.Handler;

import java.io.File;

import nep.timeline.cirno.GlobalVars;
import nep.timeline.cirno.threads.Handlers;

public class ConfigFileObserver extends FileObserver {
    public ConfigFileObserver() {
        super(GlobalVars.CONFIG_DIR, DELETE | DELETE_SELF | MODIFY | MOVE_SELF);
        reInit();
        ConfigManager.readConfig();
    }

    @Override
    public void onEvent(int event, String path) {
        Handler handler = Handlers.config;
        handler.removeCallbacksAndMessages(null);
        switch (event & ALL_EVENTS) {
            case DELETE:
            case DELETE_SELF: {
                handler.postDelayed(() -> {
                    ConfigManager.readConfig();
                    reInit();
                }, 2000);
                break;
            }
            case MODIFY:
            case MOVE_SELF: {
                handler.postDelayed(ConfigManager::readConfig, 2000);
            }
        }
    }

    public void reInit() {
        File configDir = new File(GlobalVars.CONFIG_DIR);
        if (!configDir.exists())
            configDir.mkdir();
        File logDir = new File(GlobalVars.LOG_DIR);
        if (!logDir.exists())
            logDir.mkdir();
    }
}
