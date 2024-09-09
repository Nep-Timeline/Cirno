package nep.timeline.cirno.hooks.android.binder;

import de.robv.android.xposed.XC_MethodHook;
import nep.timeline.cirno.framework.AbstractMethodHook;
import nep.timeline.cirno.framework.MethodHook;
import nep.timeline.cirno.services.FreezerService;
import nep.timeline.cirno.utils.SystemChecker;

public class HansKernelUnfreezeHook extends MethodHook {
    public HansKernelUnfreezeHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getTargetClass() {
        return "com.android.server.am.OplusHansManager";
    }

    @Override
    public String getTargetMethod() {
        return "unfreezeForKernel";
    }

    @Override
    public Object[] getTargetParam() {
        return new Object[] { int.class, int.class, int.class, int.class, int.class, String.class, int.class };
    }

    @Override
    public XC_MethodHook getTargetHook() {
        return new AbstractMethodHook() {
            @Override
            protected void beforeMethod(MethodHookParam param) {
                int type = (int) param.args[0];
                if (type != 1) // Sync binder
                    return;
                int target = (int) param.args[4];

                FreezerService.temporaryUnfreezeIfNeed(target, "Binder", 3000);
            }
        };
    }

    @Override
    public boolean isIgnoreError() {
        return !SystemChecker.isOplus(classLoader);
    }
}
