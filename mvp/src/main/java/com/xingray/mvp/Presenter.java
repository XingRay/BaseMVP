package com.xingray.mvp;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : leixing
 * @date : 2017-04-14
 * Email       : leixing1012@qq.com
 * Version     : 0.0.1
 * <p>
 * Description : MVP架构的Presenter的基类，使用JDK的动态代理，代理视图对象的方法调用
 */

@SuppressWarnings("unused")
public class Presenter<V extends LifeCycleProvider> implements MvpPresenter<V> {

    private Class<?>[] mViewInterfaces;
    private LifeCycle mLifeCycle = LifeCycle.INIT;
    private List<PresenterTask> mTasks;
    private HashMap<Long, V> mViewProxies;
    private V mViewProxy;

    V mView;

    public Presenter(Class<V> cls) {
        mViewInterfaces = new Class[]{cls};
    }

    /**
     * 获取视图对象
     *
     * @return 代理的视图对象
     */
    final protected V getView() {
        if (mViewProxy == null) {
            if (mViewInterfaces == null) {
                throw new NullPointerException("must call setViewInterface to set mViewInterfaces");
            }
            // noinspection unchecked
            mViewProxy = (V) Proxy.newProxyInstance(getClass().getClassLoader(), mViewInterfaces,
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) {
                            if (mView != null) {
                                try {
                                    method.invoke(mView, args);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }

                            PresenterTask task = new PresenterTask(
                                    Presenter.this, method, args, null);
                            if (mTasks == null) {
                                mTasks = new LinkedList<>();
                            }
                            mTasks.add(task);

                            return null;
                        }
                    });
        }
        return mViewProxy;
    }

    final protected V getResumeViewLast() {
        return runOnLifeCycles(AddStrategy.OVERRIDE, LifeCycle.RESUME);
    }

    final protected V getResumeViewOnce() {
        return runOnLifeCycles(AddStrategy.ADD_IF_NOT_EXIST, LifeCycle.RESUME);
    }

    final protected V getResumeView() {
        return runOnLifeCycles(AddStrategy.INSERT_TAIL, LifeCycle.RESUME);
    }

    protected V runOnLifeCycles(LifeCycle lifeCycle) {
        return runOnLifeCycles(AddStrategy.INSERT_TAIL);
    }

    @SuppressWarnings({"SameParameterValue", "WeakerAccess"})
    protected V runOnLifeCycles(AddStrategy<PresenterTask> strategy, LifeCycle lifeCycle) {
        if (mViewProxies == null) {
            mViewProxies = new HashMap<>(2);
        }

        long key = getProxyKey(strategy, lifeCycle);
        V view = mViewProxies.get(key);
        if (view == null) {
            if (mViewInterfaces == null) {
                throw new NullPointerException("must call setViewInterface to set mViewInterfaces");
            }
            // noinspection unchecked
            view = (V) Proxy.newProxyInstance(getClass().getClassLoader(), mViewInterfaces,
                    createInvocationHandler(strategy, new LifeCycle[]{lifeCycle}));
            mViewProxies.put(key, view);
        }
        return view;
    }

    @SuppressWarnings({"SameParameterValue"})
    final protected V runOnLifeCycles(AddStrategy<PresenterTask> strategy, LifeCycle... lifeCycles) {
        if (mViewProxies == null) {
            mViewProxies = new HashMap<>(2);
        }

        long key = getProxyKey(strategy, lifeCycles);

        V view = mViewProxies.get(key);
        if (view == null) {
            if (mViewInterfaces == null) {
                throw new NullPointerException("must call setViewInterface to set mViewInterfaces");
            }
            // noinspection unchecked
            view = (V) Proxy.newProxyInstance(getClass().getClassLoader(), mViewInterfaces,
                    createInvocationHandler(strategy, lifeCycles));
            mViewProxies.put(key, view);
        }
        return view;
    }

    private InvocationHandler createInvocationHandler(final AddStrategy<PresenterTask> strategy,
                                                      final LifeCycle[] lifeCycles) {
        return new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (mView != null && Util.contains(lifeCycles, mLifeCycle)) {
                    method.invoke(mView, args);
                    return null;
                }

                PresenterTask task = new PresenterTask(Presenter.this, method, args, lifeCycles);
                if (mTasks == null) {
                    mTasks = new LinkedList<>();
                }
                strategy.addTask(mTasks, task);

                return null;
            }
        };
    }

    @SuppressWarnings("WeakerAccess")
    protected long getProxyKey(AddStrategy strategy, LifeCycle[] lifeCycles) {
        long key = strategy.hashCode();
        for (LifeCycle lifeCycle : lifeCycles) {
            key += 1 << lifeCycle.ordinal();
        }
        return key;
    }

    @SuppressWarnings("WeakerAccess")
    protected long getProxyKey(AddStrategy strategy, LifeCycle lifeCycle) {
        long key = strategy.hashCode();
        key += 1 << lifeCycle.ordinal();
        return key;
    }

    @Override
    public void bindView(V view) {
        mView = view;
        updateLifeCycle(view.getLifeCycle());
        addLifeCycleObserver(view);
        executeTasks();
    }

    private void updateLifeCycle(LifeCycle lifeCycle) {
        mLifeCycle = lifeCycle;
        if (mLifeCycle == LifeCycle.DESTROY) {
            onViewDestroy();
        }
    }

    @SuppressWarnings("WeakerAccess")
    protected void onViewDestroy() {
        mView = null;
        if (mTasks != null) {
            mTasks.clear();
        }

        mViewProxy = null;
        if (mViewProxies != null) {
            mViewProxies.clear();
        }
    }

    private void addLifeCycleObserver(final LifeCycleProvider lifeCycleProvider) {
        lifeCycleProvider.addLifeCycleObserver(new LifeCycleObserver() {
            @Override
            public void notifyLifeCycleChanged(LifeCycle lifeCycle) {
                updateLifeCycle(lifeCycle);
                executeTasks();
            }
        });
    }

    private void executeTasks() {
        if (mView == null || mTasks == null || mTasks.isEmpty()) {
            return;
        }

        Iterator<PresenterTask> iterator = mTasks.iterator();
        while (iterator.hasNext()) {
            PresenterTask task = iterator.next();
            LifeCycle[] lifeCycles = task.getLifeCycles();
            if (lifeCycles == null || Util.contains(lifeCycles, mLifeCycle)) {
                task.run();
                iterator.remove();
            }
        }
    }
}
