package com.xingray.mvp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * xxx
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/7/10 21:10
 */
public class PresenterHolder<P> implements LifeCycleProvider, LifeCycleObserver, InvocationHandler, MvpView<P> {

    private CopyOnWriteArrayList<LifeCycleObserver> mLifeCycleObservers;
    private P mPresenter;
    private Class[] mPresenterInterfaces;
    private P mPresenterProxy;
    private List<Runnable> mTasks;
    private LifeCycle mLifeCycle = LifeCycle.INIT;

    @Override
    public boolean hasPresenter() {
        return mPresenter != null;
    }

    @Override
    public void setPresenterInterface(Class<P> cls) {
        mPresenterInterfaces = new Class[]{cls};
    }

    @Override
    public void addLifeCycleObserver(LifeCycleObserver observer) {
        if (mLifeCycleObservers == null) {
            mLifeCycleObservers = new CopyOnWriteArrayList<>();
        }
        mLifeCycleObservers.add(observer);
    }

    @Override
    public void removeLifeCycleObserver(LifeCycleObserver observer) {
        if (mLifeCycleObservers == null) {
            return;
        }
        mLifeCycleObservers.remove(observer);
    }

    @Override
    public LifeCycle getLifeCycle() {
        return mLifeCycle;
    }

    @Override
    public void notifyLifeCycleChanged(LifeCycle lifeCycle) {
        mLifeCycle = lifeCycle;
        if (mLifeCycleObservers != null) {
            for (LifeCycleObserver observer : mLifeCycleObservers) {
                observer.notifyLifeCycleChanged(lifeCycle);
            }
        }
    }

    @Override
    public void bindPresenter(P presenter) {
        mPresenter = presenter;
        executeTasks();
    }

    private void executeTasks() {
        if (mTasks == null || mTasks.isEmpty()) {
            return;
        }

        Iterator<Runnable> iterator = mTasks.iterator();
        while (iterator.hasNext()) {
            Runnable runnable = iterator.next();
            runnable.run();
            iterator.remove();
        }
    }

    @Override
    public P getPresenter() {
        if (mPresenter != null) {
            return mPresenter;
        }

        if (mPresenterProxy == null) {
            if (mPresenterInterfaces == null) {
                throw new NullPointerException("must call setPresenterInterface to set mPresenterInterfaces");
            }
            // noinspection unchecked
            mPresenterProxy = (P) Proxy.newProxyInstance(getClass().getClassLoader(), mPresenterInterfaces, this);
        }
        return mPresenterProxy;
    }

    @Override
    public Object invoke(Object proxy, final Method method, final Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (mPresenter != null) {
            method.invoke(mPresenter, args);
            return null;
        }

        if (mTasks == null) {
            mTasks = new LinkedList<>();
        }
        mTasks.add(new Runnable() {
            @Override
            public void run() {
                try {
                    method.invoke(mPresenter, args);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
        return null;
    }
}
