package nep.timeline.cirno.services;

import nep.timeline.cirno.entity.AppRecord;
import nep.timeline.cirno.entity.ProcessRecord;
import nep.timeline.cirno.utils.FrozenRW;

public class FreezerService {
    public static void freezer(AppRecord appRecord) {
        if (appRecord.isFrozen() || appRecord.isSystem() || appRecord.getAppState().isVisible())
            return;

        for (ProcessRecord processRecord : appRecord.getProcessRecords()) {
            if (processRecord.isDeathProcess() || processRecord.isFrozen())
                continue;

            FrozenRW.frozen(processRecord.getRunningUid(), processRecord.getPid());
            processRecord.setFrozen(true);
        }
    }


    public static void thaw(AppRecord appRecord) {
        if (!appRecord.isFrozen() || appRecord.isSystem())
            return;

        for (ProcessRecord processRecord : appRecord.getProcessRecords()) {
            if (processRecord.isDeathProcess() || !processRecord.isFrozen())
                continue;

            FrozenRW.thaw(processRecord.getRunningUid(), processRecord.getPid());
            processRecord.setFrozen(false);
        }
    }
}
