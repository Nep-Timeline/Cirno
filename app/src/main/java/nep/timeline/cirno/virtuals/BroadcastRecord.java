package nep.timeline.cirno.virtuals;

import de.robv.android.xposed.XposedHelpers;

public class BroadcastRecord {
    private final Object instance;

    public BroadcastRecord(Object broadcastFilter) {
        this.instance = broadcastFilter;
    }

    public void setDelivery(int index, int value) {
        ((int[]) XposedHelpers.getObjectField(this.instance, "delivery"))[index] = value;
    }

    public void skippedDelivery(int index) {
        setDelivery(index, 2);
    }
}