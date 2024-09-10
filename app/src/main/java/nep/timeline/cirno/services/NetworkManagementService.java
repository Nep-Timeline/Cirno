package nep.timeline.cirno.services;

import java.lang.reflect.Array;

import de.robv.android.xposed.XposedHelpers;
import nep.timeline.cirno.entity.AppRecord;
import nep.timeline.cirno.log.Log;

public class NetworkManagementService {
    public static volatile Object instance;
    private static Object mNetdService;
    private static Class<?> UidRangeParcel;

    public static void setInstance(Object obj, ClassLoader classLoader) {
        instance = obj;
        mNetdService = XposedHelpers.getObjectField(obj, "mNetdService");
        UidRangeParcel = XposedHelpers.findClass("android.net.UidRangeParcel", classLoader);
    }

    public static void socketDestroy(AppRecord appRecord) {
        Object uidRangeParcels = Array.newInstance(UidRangeParcel, 1);
        int uid = appRecord.getUid();
        Array.set(uidRangeParcels, 0, XposedHelpers.newInstance(UidRangeParcel, uid, uid));
        XposedHelpers.callMethod(mNetdService, "socketDestroy", uidRangeParcels, new int[0]);
        Log.d(appRecord.getPackageNameWithUser() + " 断开网络连接");
    }
}
