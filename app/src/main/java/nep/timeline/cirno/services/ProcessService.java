package nep.timeline.cirno.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nep.timeline.cirno.entity.AppRecord;
import nep.timeline.cirno.entity.ProcessRecord;

public class ProcessService {
    private static final Map<String, Map<Integer, ProcessRecord>> PROCESS_NAME_MAP = new ConcurrentHashMap<>();
    private static final Object lock = new Object();

    public static void addProcessRecord(Object record) {
        ProcessRecord processRecord = new ProcessRecord(record);
        AppRecord appRecord = processRecord.getAppRecord();
        if (appRecord == null)
            return;
        synchronized (lock) {
            PROCESS_NAME_MAP.computeIfAbsent(processRecord.getProcessName(), k -> new ConcurrentHashMap<>()).put(processRecord.getRunningUid(), processRecord);
            appRecord.getProcessRecords().add(processRecord);
        }
    }

    public static void removeProcessRecord(String name, int uid) {
        synchronized (lock) {
            if (PROCESS_NAME_MAP.containsKey(name)) {
                ProcessRecord processRecord = PROCESS_NAME_MAP.computeIfAbsent(name, k -> new ConcurrentHashMap<>()).remove(uid);
                if (processRecord == null)
                    return;
                AppRecord appRecord = processRecord.getAppRecord();
                if (appRecord == null)
                    return;
                appRecord.getProcessRecords().remove(processRecord);
                if (appRecord.getProcessRecords().isEmpty())
                    appRecord.reset();
            }
        }
    }
}
