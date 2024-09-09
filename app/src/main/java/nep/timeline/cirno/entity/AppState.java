package nep.timeline.cirno.entity;

public class AppState {
    private final AppRecord parent;

    public AppState(AppRecord appRecord) {
        this.parent = appRecord;
    }
}
