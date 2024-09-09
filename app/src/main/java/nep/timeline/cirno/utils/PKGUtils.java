package nep.timeline.cirno.utils;

import android.content.pm.ApplicationInfo;
import android.os.Process;
import android.os.UserHandle;

public class PKGUtils {
    public static int getUserId(int uid) {
        return UserHandle.getUserHandleForUid(uid).hashCode();
    }

    public static boolean isSystemApp(ApplicationInfo applicationInfo) {
        if (applicationInfo == null)
            return true;

        return applicationInfo.uid < Process.FIRST_APPLICATION_UID || (applicationInfo.flags & (ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) != 0;
    }
}
