package nep.timeline.cirno.entity;

import lombok.Getter;

@Getter
public class AppState {
    private final AppRecord parent;

    public AppState(AppRecord appRecord) {
        this.parent = appRecord;
    }
}
