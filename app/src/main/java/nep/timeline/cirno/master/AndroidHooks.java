package nep.timeline.cirno.master;

import android.os.FileObserver;

import nep.timeline.cirno.configs.ConfigFileObserver;
import nep.timeline.cirno.hooks.android.activity.ActivityManagerServiceHook;
import nep.timeline.cirno.hooks.android.activity.ActivityStatsHook;
import nep.timeline.cirno.hooks.android.alarms.AlarmManagerService;
import nep.timeline.cirno.hooks.android.binder.HansKernelUnfreezeHook;
import nep.timeline.cirno.hooks.android.binder.MilletBinderTransHook;
import nep.timeline.cirno.hooks.android.binder.SamsungBinderTransHook;
import nep.timeline.cirno.hooks.android.broadcast.BroadcastDeliveryHook;
import nep.timeline.cirno.hooks.android.broadcast.BroadcastSkipHook;
import nep.timeline.cirno.hooks.android.process.ProcessAddHook;
import nep.timeline.cirno.hooks.android.process.ProcessRemoveHook;
import nep.timeline.cirno.hooks.android.wakelock.WakeLockHook;
import nep.timeline.cirno.services.BinderService;

public class AndroidHooks {
    public static void start(ClassLoader classLoader) {
        // Config
        FileObserver fileObserver = new ConfigFileObserver();
        fileObserver.startWatching();

        // Alarms
        new AlarmManagerService(classLoader);
        // Broadcast
        new BroadcastSkipHook(classLoader);
        new BroadcastDeliveryHook(classLoader);
        // WakeLock
        new WakeLockHook(classLoader);
        // Activity
        new ActivityManagerServiceHook(classLoader);
        new ActivityStatsHook(classLoader);
        // Process
        new ProcessAddHook(classLoader);
        new ProcessRemoveHook(classLoader);
        // Binder
        new HansKernelUnfreezeHook(classLoader);
        new MilletBinderTransHook(classLoader);
        new SamsungBinderTransHook(classLoader);
        // ReKernel
        BinderService.start(classLoader);
    }
}
