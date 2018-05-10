package com.arkhipov.ayur.mytasksfirebase.log;


import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Log {


    public static void l() {
        android.util.Log.d(getLocation(), "");
    }

    public static void d(String msg) {
        android.util.Log.d(getLocation(),/* getLocation() + */msg + " ---------------------------------------------------- ");
    }

    public static void w(String msg,@Nullable Exception e) {
        android.util.Log.w(getLocation(), msg + " ------------------------------ ", e);
    }

    public static void w(String msg) {
        android.util.Log.w(getLocation(), msg + "");
    }

    private static String getLocation() {
        final String className = Log.class.getName();
        final StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        boolean found = false;

        for (int i = 0; i < traces.length; i++) {
            StackTraceElement trace = traces[i];

            try {
                if (found) {
                    if (!trace.getClassName().startsWith(className)) {
                        Class<?> clazz = Class.forName(trace.getClassName());
                        return "[" + getClassName(clazz) + ":" + trace.getMethodName() + ":" + trace.getLineNumber() + "]: ";
                    }
                }
                else if (trace.getClassName().startsWith(className)) {
                    found = true;
                    continue;
                }
            }
            catch (ClassNotFoundException e) {
            }
        }

        return "[]: ";
    }

    private static String getClassName(Class<?> clazz) {
        if (clazz != null) {
            if (!TextUtils.isEmpty(clazz.getSimpleName())) {
                return clazz.getSimpleName();
            }

            return getClassName(clazz.getEnclosingClass());
        }

        return "";
    }
}
