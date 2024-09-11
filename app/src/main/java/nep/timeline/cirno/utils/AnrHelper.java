package nep.timeline.cirno.utils;

import de.robv.android.xposed.XC_MethodHook;
import nep.timeline.cirno.entity.AppRecord;
import nep.timeline.cirno.services.ProcessService;
import nep.timeline.cirno.virtuals.ProcessRecord;

public class AnrHelper {
    public static void processingAnr(XC_MethodHook.MethodHookParam param, Object app) {
        if (app == null)
            return;
        ProcessRecord processRecord = ProcessService.getProcessRecord(app);
        if (processRecord == null)
            return;
        AppRecord appRecord = processRecord.getAppRecord();
        if (appRecord == null)
            return;
        if (!appRecord.isSystem())
            param.setResult(null);
    }
}
