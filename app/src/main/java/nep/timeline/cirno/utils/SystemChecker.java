package nep.timeline.cirno.utils;

import de.robv.android.xposed.XposedHelpers;

public class SystemChecker {
    public static boolean isSamsung(ClassLoader classLoader)
    {
        return XposedHelpers.findClassIfExists("com.android.server.am.FreecessController", classLoader) != null;
    }

    public static boolean isXiaomi(ClassLoader classLoader)
    {
        return XposedHelpers.findClassIfExists("com.miui.server.greeze.GreezeManagerService", classLoader) != null;
    }

    public static boolean isOplus(ClassLoader classLoader)
    {
        return XposedHelpers.findClassIfExists("com.android.server.am.OplusHansManager", classLoader) != null;
    }

    public static boolean isHuawei(ClassLoader classLoader)
    {
        return XposedHelpers.findClassIfExists("com.huawei.turbozone.ITurboService", classLoader) != null;
    }
}
