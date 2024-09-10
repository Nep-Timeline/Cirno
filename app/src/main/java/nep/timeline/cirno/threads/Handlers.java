package nep.timeline.cirno.threads;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;

import nep.timeline.cirno.GlobalVars;
import nep.timeline.cirno.log.Log;

public class Handlers {
    public static final Handler log = makeHandlerBackground("Log");
    public static final Handler config = makeHandlerBackground("Config");

    public static Handler makeHandlerForeground(String str) {
        return makeHandlerForeground(str, false);
    }

    public static Handler makeHandlerForeground(String str, boolean async) {
        if (async)
            return Handler.createAsync(makeLooperForeground(str));
        else
            return new Handler(makeLooperForeground(str));
    }

    public static Handler makeHandler(String str) {
        return makeHandler(str, false);
    }

    public static Handler makeHandler(String str, boolean async) {
        if (async)
            return Handler.createAsync(makeLooper(str));
        else
            return new Handler(makeLooper(str));
    }

    public static Handler makeHandlerBackground(String str) {
        return makeHandlerBackground(str, false);
    }

    public static Handler makeHandlerBackground(String str, boolean async) {
        if (async)
            return Handler.createAsync(makeLooperBackground(str));
        else return new Handler(makeLooperBackground(str));
    }

    public static Looper makeLooperForeground(String str) {
        HandlerThread handlerThread = new HandlerThread(GlobalVars.TAG + "-" + str, Process.THREAD_PRIORITY_FOREGROUND);
        handlerThread.setUncaughtExceptionHandler((t, e) -> Log.e("线程 " + t.getName() + " 出现异常: " + e));
        handlerThread.start();
        return handlerThread.getLooper();
    }

    public static Looper makeLooperBackground(String str) {
        HandlerThread handlerThread = new HandlerThread(GlobalVars.TAG + "-" + str, Process.THREAD_PRIORITY_BACKGROUND);
        handlerThread.setUncaughtExceptionHandler((t, e) -> Log.e("线程 " + t.getName() + " 出现异常: " + e));
        handlerThread.start();
        return handlerThread.getLooper();
    }

    public static Looper makeLooper(String str) {
        HandlerThread handlerThread = new HandlerThread(GlobalVars.TAG + "-" + str);
        handlerThread.setUncaughtExceptionHandler((t, e) -> Log.e("线程 " + t.getName() + " 出现异常: " + e));
        handlerThread.start();
        return handlerThread.getLooper();
    }
}
