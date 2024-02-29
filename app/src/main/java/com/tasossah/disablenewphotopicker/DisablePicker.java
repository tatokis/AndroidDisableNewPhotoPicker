package com.tasossah.disablenewphotopicker;

import android.util.Log;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.IXposedHookLoadPackage;

public class DisablePicker implements IXposedHookLoadPackage {
    static String tag = "DisableNewPhotoPicker";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!(lpparam.packageName.equals("com.google.android.providers.media.module") || lpparam.packageName.equals("com.android.providers.media.module")))
            return;

        Log.d(tag, "findAndHookMethod");
        XposedHelpers.findAndHookMethod("com.android.providers.media.MediaProvider", lpparam.classLoader,
                "setComponentEnabledSetting", String.class, boolean.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        if (((String) param.args[0]).equals("PhotoPickerGetContentActivity")) {
                            param.args[1] = false;
                        }
                    }
                }
        );
        Log.d(tag, "Done");
    }
}
