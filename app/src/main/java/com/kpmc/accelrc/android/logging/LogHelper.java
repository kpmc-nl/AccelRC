package com.kpmc.accelrc.android.logging;

/**
 * Created by matthijs on 29/08/15.
 */
public final class LogHelper {

    private LogHelper() {
    }

    public static <T> Logger getLogger(T object) {
        return new LogcatClassTagLoggerImpl(object.getClass());
    }

}
