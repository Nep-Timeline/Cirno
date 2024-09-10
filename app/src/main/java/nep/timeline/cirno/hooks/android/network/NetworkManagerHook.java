package nep.timeline.cirno.hooks.android.network;

import android.os.Build;

import de.robv.android.xposed.XC_MethodHook;
import nep.timeline.cirno.framework.AbstractMethodHook;
import nep.timeline.cirno.framework.MethodHook;
import nep.timeline.cirno.services.NetworkManagementService;

public class NetworkManagerHook extends MethodHook {
    public NetworkManagerHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getTargetClass() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) ? "com.android.server.net.NetworkManagementService" : "com.android.server.NetworkManagementService";
    }

    @Override
    public String getTargetMethod() {
        return "systemReady";
    }

    @Override
    public Object[] getTargetParam() {
        return new Object[0];
    }

    @Override
    public XC_MethodHook getTargetHook() {
        return new AbstractMethodHook() {
            @Override
            protected void afterMethod(XC_MethodHook.MethodHookParam param) {
                NetworkManagementService.setInstance(param.thisObject, classLoader);
            }
        };
    }
}
