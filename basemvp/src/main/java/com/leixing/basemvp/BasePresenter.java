package com.leixing.basemvp;

import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author : leixing
 * @date : 2017-04-14
 * Email       : leixing1012@qq.com
 * Version     : 0.0.1
 * <p>
 * Description : MVP架构的Presenter的基类，使用JDK的动态代理，代理视图对象的方法调用
 */

@SuppressWarnings("unused")
public abstract class BasePresenter<View> {
    private Map<LifeCycle, Map<TaskAddStrategy, View>> mProxies;
    private Class<?>[] mViewInterfaces;
    private WeakReference<View> mViewReference;
    private LifeCycle mLifeCycle = LifeCycle.INIT;
    private List<Task> mTasks;
    private View mViewProxy;

    /**
     * 获取视图对象
     *
     * @return 代理的视图对象
     */
    final protected View getView() {
        return runOnLifeCycles(TaskAddStrategy.INSERT_TAIL, LifeCycle.values());
    }

    final protected View runLastOnResume() {
        return runOnLifeCycles(TaskAddStrategy.OVERRIDE, LifeCycle.RESUME);
    }

    final protected View runOnceOnResume() {
        return runOnLifeCycles(TaskAddStrategy.ADD_IF_NOT_EXIST, LifeCycle.RESUME);
    }

    final protected View runOnResume() {
        return runOnLifeCycles(TaskAddStrategy.INSERT_TAIL, LifeCycle.RESUME);
    }

    final protected View runOnLifeCycles(TaskAddStrategy strategy, LifeCycle... lifeCycles) {
        if (mViewProxy == null) {
            mViewProxy = createViewProxy(lifeCycles, strategy);
        }
        return mViewProxy;
    }

    private View createViewProxy(final LifeCycle[] lifeCycles, final TaskAddStrategy strategy) {
        // noinspection unchecked
        return (View) Proxy.newProxyInstance(getClass().getClassLoader(), mViewInterfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
                final View targetView = mViewReference.get();
                if (targetView == null) {
                    return null;
                }
                if (Util.contains(lifeCycles, mLifeCycle)) {
                    method.invoke(targetView, args);
                    return null;
                }
                String id = method.getDeclaringClass().getCanonicalName() + "#" + method.getName();
                Task task = new Task(id, lifeCycles) {
                    @Override
                    public void run() {
                        final View targetView = mViewReference.get();
                        if (targetView == null) {
                            return;
                        }
                        try {
                            method.invoke(targetView, args);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                };
                if (mTasks == null) {
                    mTasks = new ArrayList<>();
                }
                strategy.addTask(mTasks, task);

                return null;
            }
        });
    }

    public void setView(@NonNull View view) {
        mViewInterfaces = view.getClass().getInterfaces();
        mViewReference = new WeakReference<>(view);

        if (view instanceof LifeCycleProvider) {
            addLifeCycleObserver((LifeCycleProvider) view);
        }
    }

    private void addLifeCycleObserver(final LifeCycleProvider lifeCycleProvider) {
        lifeCycleProvider.addLifeCycleObserver(new LifeCycleObserver() {
            @Override
            public void onCreate() {
                mLifeCycle = LifeCycle.CREATE;
                executeTasks();
            }

            @Override
            public void onStart() {
                mLifeCycle = LifeCycle.START;
                executeTasks();
            }

            @Override
            public void onResume() {
                mLifeCycle = LifeCycle.RESUME;
                executeTasks();
            }

            @Override
            public void onPause() {
                mLifeCycle = LifeCycle.PAUSE;
                executeTasks();
            }

            @Override
            public void onStop() {
                mLifeCycle = LifeCycle.STOP;
                executeTasks();
            }

            @Override
            public void onDestroy() {
                mLifeCycle = LifeCycle.DESTROY;
                executeTasks();
            }
        });
    }

    private void executeTasks() {
        if (mTasks == null || mTasks.isEmpty()) {
            return;
        }
        Iterator<Task> iterator = mTasks.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (Util.contains(task.getLifeCycles(), mLifeCycle)) {
                task.run();
                iterator.remove();
            }
        }
    }
}
