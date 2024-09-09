package nep.timeline.cirno.hooks.android.activity;

import android.app.usage.UsageEvents;
import android.os.IBinder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.robv.android.xposed.XC_MethodHook;
import nep.timeline.cirno.entity.ActivityRecord;
import nep.timeline.cirno.entity.AppRecord;
import nep.timeline.cirno.framework.AbstractMethodHook;
import nep.timeline.cirno.framework.MethodHook;

public class ActivityStatsHook extends MethodHook {
    private final Map<IBinder, ActivityRecord> activityRecords = new ConcurrentHashMap<>();
    private final List<Integer> events = List.of(UsageEvents.Event.ACTIVITY_RESUMED, UsageEvents.Event.ACTIVITY_PAUSED, UsageEvents.Event.ACTIVITY_STOPPED, UsageEvents.Event.ACTIVITY_STOPPED + 1);

    public ActivityStatsHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getTargetClass() {
        return "com.android.server.wm.ActivityTaskManagerService";
    }

    @Override
    public String getTargetMethod() {
        return "updateActivityUsageStats";
    }

    @Override
    public Object[] getTargetParam() {
        return new Object[] { "com.android.server.am.ActivityRecord", int.class };
    }

    @Override
    public XC_MethodHook getTargetHook() {
        return new AbstractMethodHook() {
            @Override
            protected void beforeMethod(MethodHookParam param) {
                int event = (int) param.args[1];

                if (!events.contains(event))
                    return;

                Object activityObject = param.args[0];
                if (activityObject == null)
                    return;

                ActivityRecord record = new ActivityRecord(activityObject);

                ActivityRecord activityRecord = (event == UsageEvents.Event.ACTIVITY_RESUMED || event == UsageEvents.Event.ACTIVITY_PAUSED) ? activityRecords.computeIfAbsent(record.getToken(), k -> record) : activityRecords.remove(record.getToken());

                if (activityRecord == null)
                    return;

                AppRecord appRecord = activityRecord.toAppRecord();

                if (appRecord == null)
                    return;

            }
        };
    }
}
