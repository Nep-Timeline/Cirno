package nep.timeline.cirno.hooks.android.audio;

import android.media.AudioPlaybackConfiguration;

import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import nep.timeline.cirno.entity.AppRecord;
import nep.timeline.cirno.framework.AbstractMethodHook;
import nep.timeline.cirno.framework.MethodHook;
import nep.timeline.cirno.handlers.AudioHandler;
import nep.timeline.cirno.services.AppService;
import nep.timeline.cirno.virtuals.AudioPlaybackConfigurationReflect;

public class AudioStateHook extends MethodHook {
    public AudioStateHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getTargetClass() {
        return AudioPlaybackConfiguration.class.getTypeName();
    }

    @Override
    public String getTargetMethod() {
        return "handleStateEvent";
    }

    @Override
    public Object[] getTargetParam() {
        return new Object[] { int.class, int.class };
    }

    @Override
    public XC_MethodHook getTargetHook() {
        return new AbstractMethodHook() {
            @Override
            protected void afterMethod(MethodHookParam param) {
                if (!((boolean) param.getResult()))
                    return;

                int event = (int) param.args[0];
                if (AudioHandler.LISTEN_EVENT.contains(event)) {
                    AudioPlaybackConfigurationReflect reflect = new AudioPlaybackConfigurationReflect((AudioPlaybackConfiguration) param.thisObject);

                    List<AppRecord> appRecords = AppService.getByUid(reflect.getClientUid());
                    if (appRecords == null)
                        return;

                    int interfaceId = reflect.getPlayerInterfaceId();
                    for (AppRecord appRecord : appRecords) {
                        if (appRecord == null)
                            continue;

                        AudioHandler.call(appRecord, event, interfaceId);
                    }
                }
            }
        };
    }
}