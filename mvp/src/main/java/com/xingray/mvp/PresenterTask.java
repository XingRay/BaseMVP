package com.xingray.mvp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * description : xxx
 *
 * @author : leixing
 * email : leixing1012@qq.com
 * @date : 2019/2/22 10:16
 */
public class PresenterTask implements Runnable {

    private final Presenter presenter;
    private final Method method;
    private final Object[] args;
    private final LifeCycle[] lifeCycles;

    PresenterTask(Presenter presenter, Method method, Object[] args, LifeCycle[] lifeCycles) {
        this.presenter = presenter;
        this.method = method;
        this.args = args;
        this.lifeCycles = lifeCycles;
    }

    LifeCycle[] getLifeCycles() {
        return lifeCycles;
    }

    @Override
    public void run() {
        try {
            method.invoke(presenter.mView, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PresenterTask that = (PresenterTask) o;
        return method.equals(that.method) &&
                Arrays.equals(lifeCycles, that.lifeCycles);
    }

    @Override
    public int hashCode() {
        int result = method.getName().hashCode();
        result = 31 * result + Arrays.hashCode(lifeCycles);
        return result;
    }
}
