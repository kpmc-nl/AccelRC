package com.kpmc.accelrc.android.logging;

import android.util.Log;

import static java.lang.String.format;

/**
 * Created by matthijs on 29/08/15.
 */
public class LogcatClassTagLoggerImpl implements Logger {

    private final String tag;

    public LogcatClassTagLoggerImpl(Class<?> context) {
        tag = context.getSimpleName();
    }

    @Override
    public void d(String fmt, Object... args) {
        Log.d(tag, format(fmt, args));
    }

    @Override
    public void i(String fmt, Object... args) {
        Log.i(tag, format(fmt, args));
    }

    @Override
    public void w(String fmt, Object... args) {
        Log.w(tag, format(fmt, args));
    }

    @Override
    public void w(Throwable e, String fmt, Object... args) {
        Log.w(tag, format(fmt, args), e);
    }

    @Override
    public void e(String fmt, Object... args) {
        Log.e(tag, format(fmt, args));
    }

    @Override
    public void e(Throwable e, String fmt, Object... args) {
        Log.e(tag, format(fmt, args), e);
    }
}
