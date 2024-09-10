package nep.timeline.cirno.configs.checkers;

import nep.timeline.cirno.GlobalVars;

public class AppConfigs {
    public static boolean isWhiteApp(String pkg, int userId) {
        return GlobalVars.applicationSettings.whiteApps.contains(pkg + "#" + userId);
    }
}
