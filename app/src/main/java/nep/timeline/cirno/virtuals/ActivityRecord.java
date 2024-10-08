package nep.timeline.cirno.virtuals;

import android.os.IBinder;

import de.robv.android.xposed.XposedHelpers;
import lombok.Getter;
import nep.timeline.cirno.entity.AppRecord;
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
        this.userId = XposedHelpers.getIntField(instance, "mUserId");
        this.token = (IBinder) XposedHelpers.getObjectField(instance, "token");
    }

    public AppRecord toAppRecord() {
        return AppService.get(packageName, userId);
    }
}
