package nep.timeline.cirno.master;

import android.os.FileObserver;

import nep.timeline.cirno.configs.ConfigFileObserver;
import nep.timeline.cirno.hooks.android.activity.ActivityManagerServiceHook;
import nep.timeline.cirno.hooks.android.activity.ActivityStatsHook;
import nep.timeline.cirno.hooks.android.binder.HansKernelUnfreezeHook;
import nep.timeline.cirno.hooks.android.binder.MilletBinderTransHook;
import nep.timeline.cirno.hooks.android.binder.SamsungBinderTransHook;
import nep.timeline.cirno.hooks.android.process.ProcessAddHook;
import nep.timeline.cirno.hooks.android.process.ProcessRemoveHook;
import nep.timeline.cirno.hooks.android.wakelock.WakeLockHook;

public class AndroidHooks {
    public static void start(ClassLoader classLoader) {
        // Config
        FileObserver fileObserver = new ConfigFileObserver();
        fileObserver.startWatching();

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
    }
}
