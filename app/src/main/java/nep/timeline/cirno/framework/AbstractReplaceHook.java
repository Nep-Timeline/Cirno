package nep.timeline.cirno.framework;

import java.lang.reflect.InvocationTargetException;

import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import nep.timeline.cirno.log.Log;

public class AbstractReplaceHook extends XC_MethodReplacement {
    protected Object replaceMethod(MethodHookParam param) throws Throwable {
        return null;
    }

    @Override
    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
        try {
            return replaceMethod(param);
        } catch (Throwable throwable) {
            Log.e(param.method.getName(), throwable);
        }
        try {
            return XposedBridge.invokeOriginalMethod(param.method, param.thisObject, param.args);
        } catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
    }
}
