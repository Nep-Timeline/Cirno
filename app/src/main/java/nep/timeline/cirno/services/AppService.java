package nep.timeline.cirno.services;

import android.content.pm.ApplicationInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nep.timeline.cirno.entity.AppRecord;

public class AppService {
    private static final Map<Integer, Map<String, AppRecord>> APP_RECORD_MAP = new ConcurrentHashMap<>();

    public static AppRecord get(String packageName, int userId) {
        if (packageName == null || packageName.equals("android"))
            return null;

        Map<String, AppRecord> appRecords = APP_RECORD_MAP.computeIfAbsent(userId, k -> new ConcurrentHashMap<>());

        if (appRecords.containsKey(packageName))
            return appRecords.get(packageName);

        ApplicationInfo applicationInfo = ActivityManagerService.getApplicationInfo(packageName, userId);
        if (applicationInfo == null)
            return null;

        return appRecords.put(packageName, new AppRecord(applicationInfo));
    }
}
