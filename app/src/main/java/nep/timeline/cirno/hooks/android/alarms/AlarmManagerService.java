package nep.timeline.cirno.hooks.android.alarms;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import nep.timeline.cirno.framework.AbstractMethodHook;
import nep.timeline.cirno.framework.MethodHook;
import nep.timeline.cirno.utils.ForceAppStandbyListener;

public class AlarmManagerService extends MethodHook {
    public AlarmManagerService(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getTargetClass() {
        return "com.android.server.alarm.AlarmManagerService";
    }

    @Override
    public String getTargetMethod() {
        return "onBootPhase";
    }

    @Override
    public Object[] getTargetParam() {
        return new Object[] { int.class };
    }

    @Override
    public XC_MethodHook getTargetHook() {
        return new AbstractMethodHook() {
            @Override
            protected void beforeMethod(MethodHookParam param) {
                ForceAppStandbyListener.setInstance(XposedHelpers.getObjectField(param.thisObject, "mForceAppStandbyListener"));
            }
        };
    }
}
