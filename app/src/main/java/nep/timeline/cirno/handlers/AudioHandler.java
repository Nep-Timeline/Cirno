package nep.timeline.cirno.handlers;

import java.util.Set;

import nep.timeline.cirno.entity.AppRecord;
import nep.timeline.cirno.log.Log;
import nep.timeline.cirno.services.FreezerService;
import nep.timeline.cirno.threads.FreezerHandler;

public class AudioHandler {
    public static final int PLAYER_STATE_RELEASED = 0;
    public static final int PLAYER_STATE_IDLE = 1;
    public static final int PLAYER_STATE_STARTED = 2;
    public static final int PLAYER_STATE_PAUSED = 3;
    public static final int PLAYER_STATE_STOPPED = 4;
    public static final Set<Integer> LISTEN_EVENT = Set.of(PLAYER_STATE_RELEASED, PLAYER_STATE_IDLE, PLAYER_STATE_STARTED, PLAYER_STATE_PAUSED, PLAYER_STATE_STOPPED);

    public static void call(AppRecord appRecord, int event, int interfaceId) {
        Set<Integer> set = appRecord.getAppState().getInterfaceIds();
        if (event == PLAYER_STATE_STARTED)
            set.add(interfaceId);
        else
            set.remove(interfaceId);

        if (set.isEmpty()) {
            if (appRecord.getAppState().setAudio(false)) {
                Log.d("应用 " + appRecord.getPackageNameWithUser() + " 停止播放音频");
                FreezerHandler.sendFreezeMessageIgnoreMessages(appRecord, 6000);
            }
        } else if (appRecord.getAppState().setAudio(true)) {
            Log.d("应用 " + appRecord.getPackageNameWithUser() + " 开始播放音频");
            FreezerService.thaw(appRecord);
        }
    }
}
