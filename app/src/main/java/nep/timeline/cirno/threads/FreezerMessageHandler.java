package nep.timeline.cirno.threads;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import nep.timeline.cirno.entity.AppRecord;
import nep.timeline.cirno.log.Log;
import nep.timeline.cirno.services.FreezerService;

public class FreezerMessageHandler extends Handler {
    public FreezerMessageHandler(Looper looper) {
        super(looper);
    }

    @Override
    public void handleMessage(@NonNull Message message) {
        super.handleMessage(message);
        try {
            FreezerService.freezer((AppRecord) message.obj);
        } catch (Throwable th) {
            Log.e("Freezer", th);
        }
    }
}
