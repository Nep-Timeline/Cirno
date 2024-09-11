package nep.timeline.cirno.utils;

public class FrozenRW {
    public static final String cgroupV2 = "/sys/fs/cgroup";

    public static void frozen(int uid, int pid) {
        RWUtils.writeFrozen(cgroupV2 + "/uid_" + uid + "/pid_" + pid + "/cgroup.freeze", 1);
    }

    public static void thaw(int uid, int pid) {
        RWUtils.writeFrozen(cgroupV2 + "/uid_" + uid + "/pid_" + pid + "/cgroup.freeze", 0);
    }
}
