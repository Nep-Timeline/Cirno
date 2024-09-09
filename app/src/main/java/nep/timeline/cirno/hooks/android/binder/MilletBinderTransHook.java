package nep.timeline.cirno.hooks.android.binder;

import de.robv.android.xposed.XC_MethodHook;
import nep.timeline.cirno.framework.AbstractMethodHook;
import nep.timeline.cirno.framework.MethodHook;
import nep.timeline.cirno.services.FreezerService;
import nep.timeline.cirno.utils.SystemChecker;

public class MilletBinderTransHook extends MethodHook {
    public MilletBinderTransHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getTargetClass() {
        return "com.miui.server.greeze.GreezeManagerService";
    }

    @Override
    public String getTargetMethod() {
        return "reportBinderTrans";
    }

    @Override
    public Object[] getTargetParam() {
        return new Object[] { int.class, int.class, int.class, int.class, int.class, boolean.class, long.class, int.class };
    }

    @Override
    public XC_MethodHook getTargetHook() {
        return new AbstractMethodHook() {
            @Override
            protected void beforeMethod(MethodHookParam param) {
                boolean isOneway = (boolean) param.args[5];
                if (isOneway)
                    return;

                int dstUid = (int) param.args[0];

                FreezerService.temporaryUnfreezeIfNeed(dstUid, "Binder", 3000);
            }
        };
    }

    @Override
    public boolean isIgnoreError() {
        return !SystemChecker.isXiaomi(classLoader);
    }
}
