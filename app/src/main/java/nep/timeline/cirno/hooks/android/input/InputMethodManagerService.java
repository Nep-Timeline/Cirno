package nep.timeline.cirno.hooks.android.input;

import android.os.Build;
import android.view.inputmethod.InputMethodInfo;

import java.util.Map;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import nep.timeline.cirno.entity.AppRecord;
import nep.timeline.cirno.framework.AbstractMethodHook;
import nep.timeline.cirno.framework.MethodHook;
import nep.timeline.cirno.services.ActivityManagerService;
import nep.timeline.cirno.services.AppService;
import nep.timeline.cirno.services.FreezerService;
import nep.timeline.cirno.threads.FreezerHandler;
import nep.timeline.cirno.utils.InputMethodData;

public class InputMethodManagerService extends MethodHook {
    public InputMethodManagerService(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getTargetClass() {
        return "com.android.server.inputmethod.InputMethodManagerService";
    }

    @Override
    public String getTargetMethod() {
        return "setInputMethodLocked";
    }

    @Override
    public Object[] getTargetParam() {
        return new Object[] { String.class, int.class };
    }

    @Override
    public XC_MethodHook getTargetHook() {
        return new AbstractMethodHook() {
            @Override
            protected void beforeMethod(MethodHookParam param) {
                String id = (String) param.args[0];
                if (id == null)
                    return;

                Object settings;

                int userId;
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    userId = XposedHelpers.getIntField(param.thisObject, "mCurrentUserId");
                    settings = XposedHelpers.callStaticMethod(XposedHelpers.findClassIfExists("com.android.server.inputmethod.InputMethodSettingsRepository", classLoader), "get", userId);
                }
                else {
                    settings = XposedHelpers.getObjectField(param.thisObject, "mSettings");
                    userId = (settings == null) ? ActivityManagerService.getCurrentOrTargetUserId() : (int) XposedHelpers.callMethod(settings, "getUserId");
                }

                synchronized (InputMethodData.class) {
                    if (InputMethodData.instance == null) {
                        InputMethodData.instance = param.thisObject;
                        if (settings != null) {
                            Object map = XposedHelpers.getObjectField(settings, "mMethodMap");
                            if (map != null) {
                                if (map.getClass().getTypeName().equals("com.android.server.inputmethod.InputMethodMap"))
                                    InputMethodData.inputMethods = (Map<String, InputMethodInfo>) XposedHelpers.getObjectField(map, "mMap");
                                else
                                    InputMethodData.inputMethods = (Map<String, InputMethodInfo>) map;
                            }
                            else
                                InputMethodData.inputMethods = null;
                        }
                        else
                            InputMethodData.inputMethods = null;
                    }

                    Map<String, InputMethodInfo> inputMethodMap = InputMethodData.inputMethods;
                    if (inputMethodMap == null)
                        return;

                    InputMethodInfo inputMethodInfo = inputMethodMap.get(id);

                    if (inputMethodInfo != null && !inputMethodInfo.equals(InputMethodData.currentInputMethodInfo)) {
                        InputMethodData.currentInputMethodInfo = inputMethodInfo;
                        AppRecord appRecord = AppService.get(inputMethodInfo.getPackageName(), userId);
                        if (appRecord != InputMethodData.currentInputMethodApp) {
                            AppRecord oldApp = InputMethodData.currentInputMethodApp;
                            InputMethodData.currentInputMethodApp = appRecord;
                            if (appRecord != null)
                                FreezerService.thaw(appRecord);
                            if (oldApp != null)
                                FreezerHandler.sendFreezeMessage(oldApp, 3000);
                        }
                    }
                }
            }
        };
    }
}
