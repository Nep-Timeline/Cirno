package nep.timeline.cirno.services;

import android.content.pm.ApplicationInfo;
import android.os.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nep.timeline.cirno.entity.AppRecord;
import nep.timeline.cirno.utils.PKGUtils;

public class AppService {
    private static final Map<Integer, Map<String, AppRecord>> APP_RECORD_MAP = new ConcurrentHashMap<>();
    private static final Map<Integer, List<AppRecord>> UID_RECORD_MAP = new ConcurrentHashMap<>();

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

    public static List<AppRecord> getByUid(int uid) {
        try {
            if (!UID_RECORD_MAP.containsKey(uid))
                putAppToCacheByUid(uid);
            List<AppRecord> records = UID_RECORD_MAP.get(uid);
            if (records == null)
                return new ArrayList<>();
            return records;
        } catch (Throwable ignored) {

        }
        return null;
    }

    private static synchronized void putAppToCacheByUid(int uid) {
        if (uid <= Process.SYSTEM_UID) {
            UID_RECORD_MAP.put(uid, null);
            return;
        }

        String[] keys = ActivityManagerService.getPackagesForUid(uid);
        if (keys == null || keys.length == 0) {
            UID_RECORD_MAP.put(uid, null);
            return;
        }

        List<AppRecord> appRecords = new ArrayList<>();
        for (String key : keys) {
            String[] split = key.split(":");
            int userId = split.length == 1 ? PKGUtils.getUserId(uid) : Integer.parseInt(split[1].trim());
            appRecords.add(get(split[0], userId));
        }

        UID_RECORD_MAP.put(uid, appRecords);
    }
}
