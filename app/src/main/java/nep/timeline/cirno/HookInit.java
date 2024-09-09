package nep.timeline.cirno;

import java.io.File;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import nep.timeline.cirno.master.AndroidHooks;

public class HookInit implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam packageParam) {
        String packageName = packageParam.packageName;
        if (!packageName.equals("android"))
            return;

        ClassLoader classLoader = GlobalVars.classLoader = packageParam.classLoader;

        try {
            File source = new File(GlobalVars.LOG_DIR, "current.log");
            File dest = new File(GlobalVars.LOG_DIR, "last.log");
            boolean delete = dest.delete();
            boolean renameTo = source.renameTo(dest);
            AndroidHooks.start(classLoader);
        } catch (Throwable throwable) {
            XposedBridge.log("Cirno (" + packageName + ") -> Hook failed:");
            XposedBridge.log(throwable);
        }
    }
}
