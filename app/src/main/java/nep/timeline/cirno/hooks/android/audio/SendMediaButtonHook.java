package nep.timeline.cirno.hooks.android.audio;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import nep.timeline.cirno.framework.AbstractMethodHook;
import nep.timeline.cirno.log.Log;
import nep.timeline.cirno.services.FreezerService;

public class SendMediaButtonHook {
    public SendMediaButtonHook(ClassLoader classLoader) {
        Class<?> targetClass = XposedHelpers.findClassIfExists("com.android.server.media.MediaSessionRecord$SessionCb", classLoader);
        if (targetClass == null)
            return;

        String fieldName = null;

        for (Field field : targetClass.getDeclaredFields()) {
            if (field.getType().getName().equals("com.android.server.media.MediaSessionRecord")) {
                fieldName = field.getName();
                break;
            }
        }

        if (fieldName == null) {
            Log.e("无法监听媒体按键!");
            return;
        }

        List<Method> methods = new ArrayList<>();
        for (Method method : targetClass.getDeclaredMethods()) {
            String methodName = method.getName();
            if (methodName.equals("sendMediaButton") || methodName.equals("play") || methodName.equals("playFromMediaId") || methodName.equals("playFromSearch") || methodName.equals("playFromUri") || methodName.equals("next") || methodName.equals("previous") || methodName.equals("seekTo"))
                methods.add(method);
        }

        for (Method method : methods) {
            try {
                String finalFieldName = fieldName;
                XposedBridge.hookMethod(method, new AbstractMethodHook() {
                    @Override
                    protected void beforeMethod(MethodHookParam param) {
                        Object record = XposedHelpers.getObjectField(param.thisObject, finalFieldName);
                        if (record == null)
                            return;

                        FreezerService.temporaryUnfreezeIfNeed(XposedHelpers.getIntField(record, "mOwnerUid"), "按下媒体按键", 3000);
                    }
                });
                Log.i(method.getName() + " -> 成功Hook完毕!");
            } catch (Throwable throwable) {
                Log.e(method.getName(), throwable);
            }
        }
    }
}
