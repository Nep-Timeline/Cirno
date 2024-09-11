package nep.timeline.cirno.hooks.android.anr;

import java.lang.reflect.Method;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import nep.timeline.cirno.framework.AbstractMethodHook;
import nep.timeline.cirno.utils.AnrHelper;

public class ANRHelperHooks {
    public final Integer findIndex(Class<?>[] parameterTypes, String clazz) {
        for (int i = 0; i < parameterTypes.length; i++)
            if (clazz.equals(parameterTypes[i].getName()))
                return i;
        return null;
    }

    public ANRHelperHooks(ClassLoader classLoader) {
        try {
            Class<?> targetClass = XposedHelpers.findClassIfExists("com.android.server.am.AnrHelper", classLoader);

            if (targetClass == null)
                return;

            for (Method method : targetClass.getDeclaredMethods()) {
                if ((method.getName().equals("appNotResponding") || method.getName().equals("deferAppNotResponding")) && method.getReturnType().equals(void.class)) {
                    Integer index = findIndex(method.getParameterTypes(), "com.android.server.am.ProcessRecord");
                    if (index == null) { // Not found
                        Integer MIUIRecordIndex = findIndex(method.getParameterTypes(), "com.android.server.am.AnrHelper$AnrRecord");
                        if (MIUIRecordIndex != null) {
                            XposedBridge.hookMethod(method, new AbstractMethodHook() {
                                @Override
                                protected void beforeMethod(MethodHookParam param) {
                                    Object anrRecord = param.args[MIUIRecordIndex];
                                    if (anrRecord == null)
                                        return;
                                    Object app = XposedHelpers.getObjectField(anrRecord, "mApp");
                                    if (app == null)
                                        return;
                                    AnrHelper.processingAnr(param, app);
                                }
                            });
                        }
                    } else {
                        XposedBridge.hookMethod(method, new AbstractMethodHook() {
                            @Override
                            protected void beforeMethod(MethodHookParam param) {
                                Object record = param.args[index];
                                if (record == null)
                                    return;
                                AnrHelper.processingAnr(param, record);
                            }
                        });
                    }
                }
            }
        } catch (Throwable ignored) {

        }
    }
}
