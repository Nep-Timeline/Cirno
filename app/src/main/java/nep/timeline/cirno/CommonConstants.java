package nep.timeline.cirno;

import java.util.Set;

public interface CommonConstants {
    String NATIVE_PACKAGE_NAME = BuildConfig.APPLICATION_ID;
    String SHELL = "com.android.shell";
    String ANDROID = "android";
    Set<String> whiteApps = Set.of(
            NATIVE_PACKAGE_NAME,
            ANDROID,
            SHELL,
            "org.lsposed.manager", /* LSPosed管理器 */
            "moe.shizuku.privileged.api", /* Shizuku */
            "com.google.android.gsf", /* 谷歌服务框架 */
            "com.xiaomi.xmsf", /* 小米服务框架 */
            "com.huawei.hwid", /* HMS核心 */
            "io.heckel.ntfy", /* NTFY */
            "com.xiaomi.smarthome", /* 米家 */
            "com.mfashiongallery.emag", /* 小米画报 */
            "com.miui.miwallpaper.moon", /* 月球超级壁纸 */
            "com.miui.miwallpaper.snowmountain", /* 雪山超级壁纸 */
            "com.miui.miwallpaper.saturn", /* 土星超级壁纸 */
            "com.miui.miwallpaper.geometry", /* 几何超级壁纸 */
            "com.miui.miwallpaper.mars", /* 火星超级壁纸 */
            "com.miui.miwallpaper.earth", /* 地球超级壁纸 */
            "com.android.systemui", /* 系统界面 */
            "miui.systemui.plugin", /* 系统界面组件 */
            "com.xiaomi.mibrain.speech", /* 系统语音引擎 */
            "com.miui.securitymanager", /* 手机管家 */
            "com.miui.home", /* 小米桌面 */
            "com.miui.gallery", /* 小米相册 */
            "com.miui.mediaeditor", /* 小米相册 - 编辑 */
            "com.android.deskclock", /* 时钟 */
            "com.android.soundrecorder", /* 录音机 */
            "com.android.calendar", /* 日历 */
            "com.miui.screenrecorder", /* 小米屏幕录制 */
            "com.miui.weather2", /* 小米天气 */
            "com.miui.notes", /* 小米笔记 */
            "com.coloros.alarmclock", /* OPPO时钟 */
            "com.coloros.soundrecorder", /* OPPO录音机 */
            "com.coloros.calendar", /* OPPO日历 */
            "com.oplus.melody", /* OPPO无线耳机 */
            "io.github.vvb2060.magisk", /* Magisk Alpha */
            "io.github.vvb2060.magisk.lite", /* Magisk Lite */
            "io.github.huskydg.magisk", /* Magisk Delta / Kitsune Mask */
            "com.topjohnwu.magisk", /* Magisk */
            "com.thehitman7.cygisk" /* Cygisk */
    );

    static boolean isWhitelistApps(String packageName)
    {
        return whiteApps.contains(packageName);
    }
}
