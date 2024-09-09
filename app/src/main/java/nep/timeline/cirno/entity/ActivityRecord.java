package nep.timeline.cirno.entity;

import android.os.IBinder;

import de.robv.android.xposed.XposedHelpers;
import lombok.Getter;
import nep.timeline.cirno.services.AppService;

@Getter
public class ActivityRecord {
    private final Object instance;
    private final String packageName;
    private final int userId;
    private final IBinder token;

    public ActivityRecord(Object instance) {
        this.instance = instance;
        this.packageName = (String) XposedHelpers.getObjectField(instance, "packageName");
        this.userId = XposedHelpers.getIntField(instance, "userId");
        this.token = (IBinder) XposedHelpers.getObjectField(instance, "token");
    }

    public AppRecord toAppRecord() {
        return AppService.get(packageName, userId);
    }
}
