package nep.timeline.cirno.utils;

import android.view.inputmethod.InputMethodInfo;

import java.util.HashMap;
import java.util.Map;

import nep.timeline.cirno.entity.AppRecord;

public class InputMethodData {
    public static volatile Object instance;
    public static Map<String, InputMethodInfo> inputMethods = new HashMap<>();
    public static InputMethodInfo currentInputMethodInfo;
    public static AppRecord currentInputMethodApp;
}
