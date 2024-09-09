package nep.timeline.cirno.hooks.android.binder;

import android.os.Build;

import de.robv.android.xposed.XC_MethodHook;
import nep.timeline.cirno.framework.AbstractMethodHook;
import nep.timeline.cirno.framework.MethodHook;
import nep.timeline.cirno.services.FreezerService;
import nep.timeline.cirno.utils.SystemChecker;

public class SamsungBinderTransHook extends MethodHook {
    public SamsungBinderTransHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getTargetClass() {
        return "com.android.server.am.FreecessController";
    }

    @Override
    public String getTargetMethod() {
        return "reportBinderUid";
    }

    @Override
    public Object[] getTargetParam() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU)
            return new Object[] { int.class, int.class, int.class, int.class, String.class, int.class, int.class };
        return new Object[] { int.class, int.class, int.class, String.class, int.class, int.class };
    }

    @Override
    public XC_MethodHook getTargetHook() {
        return new AbstractMethodHook() {
            @Override
            protected void beforeMethod(MethodHookParam param) {
                int flags = (int) param.args[Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU ? 5 : 4];
                if (flags == 1) // Async binder
                    return;
                int uid = (int) param.args[1];

                FreezerService.temporaryUnfreezeIfNeed(uid, "Binder", 3000);
            }
        };
    }

    @Override
    public boolean isIgnoreError() {
        return !SystemChecker.isSamsung(classLoader);
    }
}
