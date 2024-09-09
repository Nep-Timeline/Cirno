package nep.timeline.cirno.framework;

import de.robv.android.xposed.XC_MethodHook;
import nep.timeline.cirno.log.Log;

public abstract class AbstractMethodHook extends XC_MethodHook {
    protected void beforeMethod(MethodHookParam param) throws Throwable {

    }

    protected void afterMethod(MethodHookParam param) throws Throwable {

    }

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
        super.beforeHookedMethod(param);
        try {
            beforeMethod(param);
        } catch (Throwable throwable) {
            Log.e(param.method.getName(), throwable);
        }
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        super.afterHookedMethod(param);
        try {
            afterMethod(param);
        } catch (Throwable throwable) {
            Log.e(param.method.getName(), throwable);
        }
    }
}
