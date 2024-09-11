package nep.timeline.cirno.hooks.android.location;

import android.os.Binder;
import android.os.IBinder;

import java.util.Set;

import de.robv.android.xposed.XC_MethodHook;
import nep.timeline.cirno.entity.AppRecord;
import nep.timeline.cirno.framework.AbstractMethodHook;
import nep.timeline.cirno.framework.MethodHook;
import nep.timeline.cirno.handlers.LocationHandler;
import nep.timeline.cirno.services.ProcessService;
import nep.timeline.cirno.threads.Handlers;
import nep.timeline.cirno.virtuals.ILocationListener;
import nep.timeline.cirno.virtuals.ProcessRecord;

public class ListenerUnregisterHook extends MethodHook {
    public ListenerUnregisterHook(ClassLoader classLoader) {
        super(classLoader);
   }

    @Override
    public String getTargetClass() {
        return "com.android.server.location.LocationManagerService";
    }

    @Override
    public String getTargetMethod() {
        return "unregisterLocationListener";
    }

    @Override
    public Object[] getTargetParam() {
        return new Object[] { "android.location.ILocationListener" };
    }

    @Override
    public XC_MethodHook getTargetHook() {
        return new AbstractMethodHook() {
            @Override
            protected void afterMethod(MethodHookParam param) {
                ILocationListener listener = new ILocationListener(param.args[0]);
                int pid = Binder.getCallingPid();

                Handlers.location.post(() -> {
                    ProcessRecord processRecord = ProcessService.getProcessRecordByPid(pid);

                    if (processRecord == null)
                        return;

                    AppRecord appRecord = processRecord.getAppRecord();

                    if (appRecord == null)
                        return;

                    Set<IBinder> set = appRecord.getAppState().getLocationListeners();
                    if (set.remove(listener.asBinder()))
                        LocationHandler.call(appRecord, set);
                });
            }
        };
    }
}