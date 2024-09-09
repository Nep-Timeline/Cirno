package nep.timeline.cirno.hooks.android.process;

import nep.timeline.cirno.framework.AbstractMethodHook;
import nep.timeline.cirno.framework.MethodHook;
import nep.timeline.cirno.services.ProcessService;

public class ProcessAddHook extends MethodHook {
    public ProcessAddHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getTargetClass() {
        return "com.android.server.am.ProcessList";
    }

    @Override
    public String getTargetMethod() {
        return "addProcessNameLocked";
    }

    @Override
    public Object[] getTargetParam() {
        return new Object[] { "com.android.server.am.ProcessRecord" };
    }

    @Override
    public AbstractMethodHook getTargetHook() {
        return new AbstractMethodHook() {
            @Override
            protected void afterMethod(MethodHookParam param) {
                Object record = param.args[0];
                if (record == null)
                    return;
                ProcessService.addProcessRecord(record);
            }
        };
    }
}