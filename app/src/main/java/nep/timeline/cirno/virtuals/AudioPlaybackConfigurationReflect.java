package nep.timeline.cirno.virtuals;

import android.media.AudioPlaybackConfiguration;

import de.robv.android.xposed.XposedHelpers;

public class AudioPlaybackConfigurationReflect {
    private final AudioPlaybackConfiguration instance;

    public AudioPlaybackConfigurationReflect(AudioPlaybackConfiguration audioRecordingConfiguration) {
        this.instance = audioRecordingConfiguration;
    }

    public int getPlayerInterfaceId() {
        return (int) XposedHelpers.callMethod(this.instance, "getPlayerInterfaceId");
    }

    public int getClientUid() {
        return (int) XposedHelpers.callMethod(this.instance, "getClientUid");
    }

    public int getClientPid() {
        return (int) XposedHelpers.callMethod(this.instance, "getClientPid");
    }

    public int getPlayerType() {
        return (int) XposedHelpers.callMethod(this.instance, "getPlayerType");
    }
}
