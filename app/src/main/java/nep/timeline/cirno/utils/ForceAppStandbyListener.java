package nep.timeline.cirno.utils;

import de.robv.android.xposed.XposedHelpers;
import lombok.Setter;
import nep.timeline.cirno.entity.AppRecord;
import nep.timeline.cirno.log.Log;

public class ForceAppStandbyListener {
    @Setter
    private static Object instance;

    public static void removeAlarmsForUid(AppRecord appRecord) {
        if (instance == null)
            return;

        XposedHelpers.callMethod(instance, "removeAlarmsForUid", appRecord.getUid());
        Log.d(appRecord.getPackageNameWithUser() + " 移除Alarms");
    }
}