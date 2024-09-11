package nep.timeline.cirno.virtuals;

import android.os.IBinder;

import de.robv.android.xposed.XposedHelpers;
import lombok.Getter;

@Getter
public class ILocationListener {
    private final Object instance;

    public ILocationListener(Object instance) {
        this.instance = instance;
    }

    public IBinder asBinder() {
        if (instance == null)
            return null;

        return (IBinder) XposedHelpers.callMethod(instance, "asBinder");
    }
}
