package com.kpmc.accelrc.dagger;

/**
 * Created by matthijs on 29/08/15.
 */
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
public @interface ApplicationContext {
}
