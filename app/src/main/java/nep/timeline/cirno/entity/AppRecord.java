package nep.timeline.cirno.entity;

import android.content.pm.ApplicationInfo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.Data;
import nep.timeline.cirno.CommonConstants;
import nep.timeline.cirno.utils.PKGUtils;

@Data
public class AppRecord {
    private final String packageName;
    private final int userId;
    private final int uid;
    private final ApplicationInfo applicationInfo;
    private AppState appState;
    private boolean frozen;
    private final List<ProcessRecord> processRecords = new CopyOnWriteArrayList<>();

    public AppRecord(ApplicationInfo applicationInfo) {
        this.packageName = applicationInfo.packageName;
        this.userId = PKGUtils.getUserId(applicationInfo.uid);
        this.uid = applicationInfo.uid;
        this.applicationInfo = applicationInfo;
        this.appState = new AppState(this);
    }

    public boolean isSystem() {
        return packageName == null || PKGUtils.isSystemApp(applicationInfo) || CommonConstants.isWhitelistApps(packageName);
    }

    public String getPackageNameWithUser() {
        if (userId == 0)
            return packageName;
        return packageName + ":" + userId;
    }

    public void reset() {
        this.frozen = false;
        this.appState = new AppState(this);
    }
}
