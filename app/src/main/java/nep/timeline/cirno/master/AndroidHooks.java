package nep.timeline.cirno.master;

import nep.timeline.cirno.hooks.android.binder.HansKernelUnfreezeHook;
import nep.timeline.cirno.hooks.android.binder.MilletBinderTransHook;
import nep.timeline.cirno.hooks.android.binder.SamsungBinderTransHook;

public class AndroidHooks {
    public static void start(ClassLoader classLoader) {
        // Binder
        new HansKernelUnfreezeHook(classLoader);
        new MilletBinderTransHook(classLoader);
        new SamsungBinderTransHook(classLoader);
    }
}
