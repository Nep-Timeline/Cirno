package nep.timeline.cirno.netlink;

import java.io.FileDescriptor;

import de.robv.android.xposed.XposedHelpers;

public class IoUtils {
    public static void closeQuietly(ClassLoader classLoader, FileDescriptor fileDescriptor) {
        XposedHelpers.callStaticMethod(XposedHelpers.findClass("libcore.io.IoUtils", classLoader), "closeQuietly", fileDescriptor);
    }

    public static void setsockoptInt(ClassLoader classLoader, FileDescriptor fileDescriptor, int level, int option, int value) {
        Class<?> libcore = XposedHelpers.findClass("libcore.io.Libcore", classLoader);
        Object os = XposedHelpers.getStaticObjectField(libcore, "os");
        XposedHelpers.callMethod(os, "setsockoptInt", fileDescriptor, level, option, value);
    }
}
