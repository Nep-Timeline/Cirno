package nep.timeline.cirno.utils;

import android.os.UserHandle;

public class PKGUtils {
    public static int getUserId(int uid) {
        return UserHandle.getUserHandleForUid(uid).hashCode();
    }
}
