package nep.timeline.cirno.hooks.android.signal;

import android.os.Process;

import de.robv.android.xposed.XC_MethodHook;
import nep.timeline.cirno.framework.AbstractMethodHook;
import nep.timeline.cirno.framework.MethodHook;
import nep.timeline.cirno.services.ProcessService;
import nep.timeline.cirno.virtuals.ProcessRecord;

public class SendSignalQuietHook extends MethodHook {
    public SendSignalQuietHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getTargetClass() {
        return Process.class.getTypeName();
    }

    @Override
    public String getTargetMethod() {
        return "sendSignalQuiet";
    }

    @Override
    public Object[] getTargetParam() {
        return new Object[] { int.class, int.class };
    }

    @Override
    public XC_MethodHook getTargetHook() {
        return new AbstractMethodHook() {
            @Override
            protected void beforeMethod(MethodHookParam param) {
                int pid = (int) param.args[0];
                int signal = (int) param.args[1];
                if (signal != Process.SIGNAL_KILL)
                    return;

                ProcessRecord processRecord = ProcessService.getProcessRecordByPid(pid);
                if (processRecord == null || processRecord.isDeathProcess())
                    return;

                ProcessService.removeProcessRecord(processRecord);
            }
        };
    }
}
