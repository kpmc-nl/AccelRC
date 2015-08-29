package com.kpmc.accelrc.android.logging;

/**
 * Created by matthijs on 29/08/15.
 */
public interface Logger {

    void d(String fmt, Object... args);

    void i(String fmt, Object... args);

    void w(String fmt, Object... args);

    void w(Throwable e, String fmt, Object... args);

    void e(String fmt, Object... args);

    void e(Throwable e, String fmt, Object... args);

}
