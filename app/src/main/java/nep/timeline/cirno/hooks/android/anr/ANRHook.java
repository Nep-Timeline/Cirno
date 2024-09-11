package nep.timeline.cirno.hooks.android.anr;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import nep.timeline.cirno.framework.AbstractMethodHook;
import nep.timeline.cirno.framework.MethodHook;
import nep.timeline.cirno.utils.AnrHelper;

public class ANRHook extends MethodHook {
    public ANRHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getTargetClass() {
        return "com.android.server.am.AnrHelper$AnrRecord";
    }

    @Override
    public String getTargetMethod() {
        return "appNotResponding";
    }

    @Override
    public Object[] getTargetParam() {
        return new Object[] { boolean.class };
    }

    @Override
    public XC_MethodHook getTargetHook() {
        return new AbstractMethodHook() {
            @Override
            protected void beforeMethod(MethodHookParam param) {
                Object app = XposedHelpers.getObjectField(param.thisObject, "mApp");
                if (app == null)
                    return;
                AnrHelper.processingAnr(param, app);
            }
        };
    }
}
