package nep.timeline.cirno.entity;

import android.os.IBinder;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

@Getter
public class AppState {
    private final AppRecord parent;
    private boolean visible = false;
    private boolean location = false;
    private boolean audio = false;
    private final Set<IBinder> activities = new HashSet<>();
    private final Set<IBinder> locationListeners = new HashSet<>();
    private final Set<Integer> interfaceIds = new HashSet<>();

    public AppState(AppRecord appRecord) {
        this.parent = appRecord;
    }

    public synchronized boolean setVisible(boolean value) {
        if (visible == value)
            return false;
        visible = value;
        return true;
    }

    public synchronized boolean setLocation(boolean value) {
        if (location == value)
            return false;
        location = value;
        return true;
    }

    public synchronized boolean setAudio(boolean value) {
        if (audio == value)
            return false;
        audio = value;
        return true;
    }
}
