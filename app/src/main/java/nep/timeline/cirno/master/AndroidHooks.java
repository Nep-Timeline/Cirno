package nep.timeline.cirno.master;

import nep.timeline.cirno.hooks.android.binder.HansKernelUnfreezeHook;
import nep.timeline.cirno.hooks.android.binder.MilletBinderTransHook;
import nep.timeline.cirno.hooks.android.binder.SamsungBinderTransHook;
import nep.timeline.cirno.hooks.android.process.ProcessAddHook;
import nep.timeline.cirno.hooks.android.process.ProcessRemoveHook;

public class AndroidHooks {
    public static void start(ClassLoader classLoader) {
        // Process
        new ProcessAddHook(classLoader);
        new ProcessRemoveHook(classLoader);
        // Binder
        new HansKernelUnfreezeHook(classLoader);
        new MilletBinderTransHook(classLoader);
        new SamsungBinderTransHook(classLoader);
    }
}
