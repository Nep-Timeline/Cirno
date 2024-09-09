package nep.timeline.cirno.services;

import nep.timeline.cirno.utils.RWUtils;

public class FreezerService {
    public static final String cgroupV2 = "/sys/fs/cgroup/";

    public static void frozen(int uid, int pid) {
        RWUtils.writeFrozen(cgroupV2 + "/uid_" + uid + "/pid_" + pid + "/cgroup.freeze", 1);
    }

    public static void thaw(int uid, int pid) {
        RWUtils.writeFrozen(cgroupV2 + "/uid_" + uid + "/pid_" + pid + "/cgroup.freeze", 0);
    }
}
