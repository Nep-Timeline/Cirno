package nep.timeline.cirno.hooks.android.anr;

import android.content.pm.ApplicationInfo;
import android.os.Build;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import nep.timeline.cirno.framework.AbstractMethodHook;
import nep.timeline.cirno.framework.MethodHook;
import nep.timeline.cirno.utils.AnrHelper;

public class ANRErrorStateHook extends MethodHook {
    public ANRErrorStateHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getTargetClass() {
        return "com.android.server.am.ProcessErrorStateRecord";
    }

    @Override
    public String getTargetMethod() {
        return "appNotResponding";
    }

    @Override
    public Object[] getTargetParam() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU)
            return new Object[] { String.class, ApplicationInfo.class, String.class, "com.android.server.wm.WindowProcessController", boolean.class, "com.android.internal.os.TimeoutRecord", ExecutorService.class, boolean.class, boolean.class, Future.class };
        return new Object[] { String.class, ApplicationInfo.class, String.class, "com.android.server.wm.WindowProcessController", boolean.class, String.class, boolean.class };
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

    @Override
    public int getMinVersion() {
        return Build.VERSION_CODES.S;
    }
}
