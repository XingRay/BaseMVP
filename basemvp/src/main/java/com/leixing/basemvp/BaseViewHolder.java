package com.leixing.basemvp;

import java.lang.reflect.Proxy;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * xxx
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing@baidu.com
 * @date : 2019/7/10 21:10
 */
public class BaseViewHolder<P> implements LifeCycleProvider, LifeCycleObserver {

    private CopyOnWriteArrayList<LifeCycleObserver> mLifeCycleObservers;
    private P mPresenter;

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
    public void onCreate() {
        if (mLifeCycleObservers != null) {
            for (LifeCycleObserver observer : mLifeCycleObservers) {
                observer.onCreate();
            }
        }
    }


    @Override
    public void onStart() {
        if (mLifeCycleObservers != null) {
            for (LifeCycleObserver observer : mLifeCycleObservers) {
                observer.onStart();
            }
        }
    }

    @Override
    public void onResume() {
        if (mLifeCycleObservers != null) {
            for (LifeCycleObserver observer : mLifeCycleObservers) {
                observer.onResume();
            }
        }
    }

    @Override
    public void onPause() {
        if (mLifeCycleObservers != null) {
            for (LifeCycleObserver observer : mLifeCycleObservers) {
                observer.onPause();
            }
        }
    }

    @Override
    public void onStop() {
        if (mLifeCycleObservers != null) {
            for (LifeCycleObserver observer : mLifeCycleObservers) {
                observer.onStop();
            }
        }
    }

    @Override
    public void onDestroy() {
        if (mLifeCycleObservers != null) {
            for (LifeCycleObserver observer : mLifeCycleObservers) {
                observer.onDestroy();
            }
            mLifeCycleObservers.clear();
        }
    }

    public void bindPresenter(P presenter){
        mPresenter = presenter;
    }

//    public P getPresenter(){
//        if(mPresenter!=null){
//            return mPresenter;
//        }else {
//           return Proxy.newProxyInstance(getClass().getClassLoader(), )
//        }
//    }
}
