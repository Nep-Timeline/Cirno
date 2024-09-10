package nep.timeline.cirno.hooks.android.broadcast;

import android.os.Build;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import nep.timeline.cirno.framework.AbstractMethodHook;
import nep.timeline.cirno.framework.MethodHook;
import nep.timeline.cirno.services.ProcessService;
import nep.timeline.cirno.utils.SystemChecker;
import nep.timeline.cirno.virtuals.BroadcastRecord;
import nep.timeline.cirno.virtuals.ProcessRecord;

public class BroadcastDeliveryHook extends MethodHook {
    public BroadcastDeliveryHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getTargetClass() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) ? "com.android.server.am.BroadcastQueueImpl" : "com.android.server.am.BroadcastQueue";
    }

    @Override
    public String getTargetMethod() {
        return "deliverToRegisteredReceiverLocked";
    }

    @Override
    public Object[] getTargetParam() {
        if (SystemChecker.isHuawei(classLoader))
            return new Object[] { "com.android.server.am.BroadcastRecord", "com.android.server.am.BroadcastFilter", boolean.class, int.class, "com.android.server.am.BroadcastRecordEx" };

        return new Object[] { "com.android.server.am.BroadcastRecord", "com.android.server.am.BroadcastFilter", boolean.class, int.class };
    }

    @Override
    public XC_MethodHook getTargetHook() {
        return new AbstractMethodHook() {
            @Override
            protected void beforeMethod(XC_MethodHook.MethodHookParam param) {
                Object record = param.args[0];
                if (record == null)
                    return;

                BroadcastRecord broadcastRecord = new BroadcastRecord(record);

                Object filter = param.args[1];
                if (filter == null)
                    return;

                Object receiver = XposedHelpers.getObjectField(filter, "receiverList");
                if (receiver == null)
                    return;

                Object app = XposedHelpers.getObjectField(receiver, "app");
                if (app == null)
                    return;

                ProcessRecord processRecord = ProcessService.getProcessRecord(app);
                if (processRecord == null)
                    return;

                if (processRecord.isFrozen()) {
                    broadcastRecord.skippedDelivery((int) param.args[3]);
                    param.setResult(null);
                }
            }
        };
    }
}
