package nep.timeline.cirno.hooks.android.activity;

import de.robv.android.xposed.XC_MethodHook;
import nep.timeline.cirno.framework.AbstractMethodHook;
import nep.timeline.cirno.framework.MethodHook;
import nep.timeline.cirno.services.ActivityManagerService;

public class ActivityManagerServiceHook extends MethodHook {
    public ActivityManagerServiceHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getTargetClass() {
        return "com.android.server.am.ActivityManagerService";
    }

    @Override
    public String getTargetMethod() {
        return "setSystemProcess";
    }

    @Override
    public Object[] getTargetParam() {
        return new Object[0];
    }

    @Override
    public XC_MethodHook getTargetHook() {
        return new AbstractMethodHook() {
            @Override
            protected void beforeMethod(MethodHookParam param) {
                ActivityManagerService.setInstance(param.thisObject);
            }
        };
    }
}
