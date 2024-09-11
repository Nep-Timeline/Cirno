package nep.timeline.cirno.handlers;

import android.os.IBinder;

import java.util.Set;

import nep.timeline.cirno.entity.AppRecord;
import nep.timeline.cirno.log.Log;
import nep.timeline.cirno.services.FreezerService;
import nep.timeline.cirno.threads.FreezerHandler;

public class LocationHandler {
    public static void call(AppRecord appRecord, Set<IBinder> set) {
        if (appRecord.isSystem())
            return;

        if (set.isEmpty()) {
            if (appRecord.getAppState().setLocation(false)) {
                Log.d("应用 " + appRecord.getPackageNameWithUser() + " 结束定位");
                FreezerHandler.sendFreezeMessage(appRecord, 3000);
            }
        } else if (appRecord.getAppState().setLocation(true)) {
            Log.d("应用 " + appRecord.getPackageNameWithUser() + " 开始定位");
            FreezerService.thaw(appRecord);
        }
    }
}
