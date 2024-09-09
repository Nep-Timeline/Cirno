package nep.timeline.cirno.services;

import android.content.pm.ApplicationInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nep.timeline.cirno.entity.AppRecord;

public class AppService {
    private static final Map<Integer, ConcurrentHashMap<String, AppRecord>> APP_RECORD_MAP = new ConcurrentHashMap<>();

    public static AppRecord get(String packageName, int userId) {
        if (packageName == null || packageName.equals("android"))
            return null;

        ApplicationInfo applicationInfo = ActivityManagerService.getApplicationInfo(packageName, userId);
        if (applicationInfo == null)
            return null;
        
        APP_RECORD_MAP.computeIfAbsent(userId, k -> new ConcurrentHashMap<>()).put(packageName, new AppRecord());

        return null;
    }
}
