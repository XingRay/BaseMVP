package com.xingray.sample.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.xingray.mvp.LifeCycle;
import com.xingray.mvp.LifeCycleObserver;
import com.xingray.mvp.MvpView;
import com.xingray.mvp.PresenterHolder;

/**
 * Description : activity的基类.
 *
 * @author : leixing
 * @date : 2017-04-14
 * Email       : leixing1012@qq.com
 * Version     : 0.0.1
 * <p>
 */

public abstract class BaseMvpActivity<P> extends FragmentActivity implements MvpView<P> {

    protected Activity mActivity;
    protected Context mContext;

    private PresenterHolder<P> mPresenterHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mContext = getApplicationContext();
        mPresenterHolder = new PresenterHolder<>();

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

        mPresenterHolder.notifyLifeCycleChanged(LifeCycle.CREATE);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mPresenterHolder.notifyLifeCycleChanged(LifeCycle.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenterHolder.notifyLifeCycleChanged(LifeCycle.RESUME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenterHolder.notifyLifeCycleChanged(LifeCycle.PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenterHolder.notifyLifeCycleChanged(LifeCycle.STOP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenterHolder.notifyLifeCycleChanged(LifeCycle.DESTROY);
    }

    @Override
    public void setPresenterInterface(Class<P> cls) {
        mPresenterHolder.setPresenterInterface(cls);
    }

    @Override
    public boolean hasPresenter() {
        return mPresenterHolder.hasPresenter();
    }

    @Override
    public void bindPresenter(P p) {
        mPresenterHolder.bindPresenter(p);
    }

    @Override
    public P getPresenter() {
        return mPresenterHolder.getPresenter();
    }

    @Override
    public void addLifeCycleObserver(LifeCycleObserver observer) {
        mPresenterHolder.addLifeCycleObserver(observer);
    }

    @Override
    public void removeLifeCycleObserver(LifeCycleObserver observer) {
        mPresenterHolder.removeLifeCycleObserver(observer);
    }

    @Override
    public LifeCycle getLifeCycle() {
        return mPresenterHolder.getLifeCycle();
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
}
