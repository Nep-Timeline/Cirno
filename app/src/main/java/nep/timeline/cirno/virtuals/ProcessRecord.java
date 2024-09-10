package nep.timeline.cirno.virtuals;

import android.content.pm.ApplicationInfo;

import de.robv.android.xposed.XposedHelpers;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import nep.timeline.cirno.entity.AppRecord;
import nep.timeline.cirno.services.AppService;

@Getter
public class ProcessRecord {
    private final Object instance;
    private final int userId;
    private final int runningUid;
    private final ApplicationInfo applicationInfo;
    private final int uid;
    private final String packageName;
    private final String processName;
    @Getter(AccessLevel.NONE)
    private AppRecord appRecord;
    @Setter
    private boolean frozen;

    public ProcessRecord(Object instance) {
        this.instance = instance;
        this.userId = XposedHelpers.getIntField(instance, "userId");
        this.runningUid = XposedHelpers.getIntField(instance, "uid");
        this.applicationInfo = (ApplicationInfo) XposedHelpers.getObjectField(instance, "info");
        this.uid = applicationInfo.uid;
        this.packageName = applicationInfo.packageName;
        this.processName = (String) XposedHelpers.getObjectField(instance, "processName");
        this.appRecord = AppService.get(packageName, userId);
    }

    public int getPid() {
        return (int) XposedHelpers.getObjectField(instance, "mPid");
    }

    public boolean isDeathProcess() {
        return getPid() <= 0;
    }

    public AppRecord getAppRecord() {
        if (appRecord == null)
            appRecord = AppService.get(packageName, userId);
        return appRecord;
    }
}
