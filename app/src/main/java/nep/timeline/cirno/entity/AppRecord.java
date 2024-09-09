package nep.timeline.cirno.entity;

import android.content.pm.ApplicationInfo;

import lombok.Data;
import nep.timeline.cirno.utils.PKGUtils;

@Data
public class AppRecord {
    private final String packageName;
    private final int userId;
    private final int uid;
    private final ApplicationInfo applicationInfo;
    private AppState appState;
    private boolean frozen;

    public AppRecord(ApplicationInfo applicationInfo) {
        this.packageName = applicationInfo.packageName;
        this.userId = PKGUtils.getUserId(applicationInfo.uid);
        this.uid = applicationInfo.uid;
        this.applicationInfo = applicationInfo;
        this.appState = new AppState(this);
    }

    public void reset() {
        this.frozen = false;
        this.appState = new AppState(this);
    }
}
