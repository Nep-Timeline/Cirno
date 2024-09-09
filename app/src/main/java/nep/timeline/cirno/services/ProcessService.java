package nep.timeline.cirno.services;

public class ProcessService {
    private static final Object lock = new Object();

    public static void addProcessRecord(Object record) {
        synchronized (lock) {

        }
    }

    public static void removeProcessRecord(Object record) {
        synchronized (lock) {

        }
    }
}
