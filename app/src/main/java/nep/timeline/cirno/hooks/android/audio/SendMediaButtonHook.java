package nep.timeline.cirno.hooks.android.audio;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import nep.timeline.cirno.framework.AbstractMethodHook;
import nep.timeline.cirno.services.FreezerService;

public class SendMediaButtonHook {
    public SendMediaButtonHook(ClassLoader classLoader) {
        Class<?> targetClass = XposedHelpers.findClassIfExists("com.android.server.media.MediaSessionRecord$SessionCb", classLoader);
        if (targetClass == null)
            return;

        List<Method> methods = new ArrayList<>();
        for (Method method : targetClass.getDeclaredMethods()) {
            String methodName = method.getName();
            if (methodName.equals("sendMediaButton") || methodName.equals("play") || methodName.equals("next") || methodName.equals("previous"))
                methods.add(method);
        }

        for (Method method : methods) {
            XposedBridge.hookMethod(method, new AbstractMethodHook() {
                @Override
                protected void beforeMethod(MethodHookParam param) {
                    String pkg = (String) param.args[0];
                    int uid = (int) param.args[2];
                    FreezerService.temporaryUnfreezeIfNeed(pkg, uid, "按下媒体按键", 3000);
                }
            });
        }
    }
}
