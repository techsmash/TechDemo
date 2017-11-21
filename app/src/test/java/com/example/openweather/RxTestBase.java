package com.example.openweather;

import android.support.annotation.CallSuper;
import android.support.annotation.VisibleForTesting;

import org.junit.After;
import org.junit.Before;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.TestScheduler;

/**
 * Base class for all tests that use RxJava/RxAndroid, setting up SchedulerHooks.
 **/
public abstract class RxTestBase {
    @VisibleForTesting
    protected TestScheduler testScheduler;

    /**
     * Registers the schedulerHooks.
     */
    @Before
    @CallSuper
    public void setUp() {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
        testScheduler = new TestScheduler();

        setJavaSchedulers();
        setAndroidSchedulers();
    }

    private void setJavaSchedulers() {
        RxJavaPlugins.setIoSchedulerHandler(scheduler111 -> testScheduler);
        RxJavaPlugins.setComputationSchedulerHandler(scheduler11 -> testScheduler);
        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler1 -> testScheduler);
        RxJavaPlugins.setSingleSchedulerHandler(scheduler -> testScheduler);
    }

    private void setAndroidSchedulers() {
        RxAndroidPlugins.onMainThreadScheduler(Schedulers.trampoline());
        RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> Schedulers.trampoline());
        RxAndroidPlugins.initMainThreadScheduler(Schedulers::trampoline);
    }

    @After
    @CallSuper
    public void tearDown() {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }
}
