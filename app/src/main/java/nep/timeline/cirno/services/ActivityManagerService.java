package nep.timeline.cirno.services;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import de.robv.android.xposed.XposedHelpers;
import lombok.Setter;

public class ActivityManagerService {
    @Setter
    public static volatile Object instance;

    public static Context getContext() {
        return (Context) XposedHelpers.getObjectField(instance, "mContext");
    }

    public static ApplicationInfo getApplicationInfo(String packageName, int userId) {
        try {
            Context context = getContext();
            if (context == null)
                return null;
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null)
                return null;
            return (ApplicationInfo) XposedHelpers.callMethod(packageManager, "getApplicationInfoAsUser", packageName, PackageManager.GET_META_DATA | PackageManager.GET_SIGNING_CERTIFICATES, userId);
        } catch (Throwable ignored) {

        }
        return null;
    }
}
