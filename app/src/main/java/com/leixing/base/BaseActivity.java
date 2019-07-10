package com.leixing.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.leixing.basemvp.LifeCycleObserver;
import com.leixing.basemvp.LifeCycleProvider;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Description : activity的基类.
 *
 * @author : leixing
 * @date : 2017-04-14
 * Email       : leixing1012@qq.com
 * Version     : 0.0.1
 * <p>
 */

public abstract class BaseActivity extends FragmentActivity implements LifeCycleProvider {

    protected Activity mActivity;
    protected Context mContext;

    private List<LifeCycleObserver> mLifeCycleObservers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mContext = getApplicationContext();

        if (!isParamsValid(getIntent())) {
            finish();
            return;
        }
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }
        initVariables();
        initView();
        loadData();

        if (mLifeCycleObservers != null) {
            for (LifeCycleObserver observer : mLifeCycleObservers) {
                observer.onCreate();
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mLifeCycleObservers != null) {
            for (LifeCycleObserver observer : mLifeCycleObservers) {
                observer.onStart();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLifeCycleObservers != null) {
            for (LifeCycleObserver observer : mLifeCycleObservers) {
                observer.onResume();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mLifeCycleObservers != null) {
            for (LifeCycleObserver observer : mLifeCycleObservers) {
                observer.onPause();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mLifeCycleObservers != null) {
            for (LifeCycleObserver observer : mLifeCycleObservers) {
                observer.onStop();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLifeCycleObservers != null) {
            for (LifeCycleObserver observer : mLifeCycleObservers) {
                observer.onDestroy();
            }
            mLifeCycleObservers.clear();
        }
    }

    /**
     * 根据调用activity的intent所携带的参数，判断activity是否可以显示
     *
     * @param intent 启动activity的参数
     * @return activity是否可以显示
     */
    protected boolean isParamsValid(@SuppressWarnings("unused") Intent intent) {
        return true;
    }

    /**
     * 恢复保存的状态
     *
     * @param state 保存的状态
     */
    protected void restoreState(Bundle state) {
    }

    /**
     * 初始化变量， 如presenter，adapter，数据列表等
     */
    protected abstract void initVariables();

    /**
     * 初始化控件，在这个方法中调用{@link Activity#setContentView(int)}设置布局， 绑定布局(通过
     * {@link Activity#findViewById(int)}或者ButterKnife{@link <a href="https://github.com/JakeWharton/butterknife"/>})。
     * 及设置监听器。
     */
    protected abstract void initView();

    /**
     * 载入数据，从服务器或者本地获取数据，然后展示在页面中。
     */
    protected abstract void loadData();

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
}