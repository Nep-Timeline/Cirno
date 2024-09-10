package nep.timeline.cirno.framework;

import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import nep.timeline.cirno.log.Log;

public abstract class MethodHook {
    public final int ANY_VERSION = -1;
    public final ClassLoader classLoader;
    private XC_MethodHook.Unhook unhook = null;

    public MethodHook(ClassLoader classLoader) {
        this.classLoader = classLoader;
        try {
            startHook();
        } catch (Throwable throwable) {
            if (!isIgnoreError())
                Log.e(getTargetMethod(), throwable);
        }
    }

    public abstract String getTargetClass();

    public abstract String getTargetMethod();

    public abstract Object[] getTargetParam();

    public abstract XC_MethodHook getTargetHook();

    public int getMinVersion() {
        return ANY_VERSION;
    }

    public boolean isIgnoreError() {
        return false;
    }

    public void startHook() {
        int minVersion = getMinVersion();
        if (minVersion == ANY_VERSION || Build.VERSION.SDK_INT >= minVersion) {
            Object[] targetParam = getTargetParam();
            XC_MethodHook targetHook = getTargetHook();

            if (targetHook == null)
                return;

            String targetMethod = getTargetMethod();
            String targetClass = getTargetClass();

            ArrayList<Object> param = new ArrayList<>(Arrays.asList(targetParam));
            param.add(targetHook);
            if (targetMethod == null)
                unhook = XposedHelpers.findAndHookConstructor(targetClass, classLoader, param.toArray());
            else
                unhook = XposedHelpers.findAndHookMethod(targetClass, classLoader, targetMethod, param.toArray());
            Log.i(getTargetMethod() + " -> 成功Hook完毕!");
        }
    }

    public void unhook() {
        if (unhook == null)
            return;

        unhook.unhook();
        unhook = null;
    }
}
