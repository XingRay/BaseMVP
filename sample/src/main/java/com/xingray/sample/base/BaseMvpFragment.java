package com.xingray.sample.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xingray.mvp.LifeCycle;
import com.xingray.mvp.LifeCycleObserver;
import com.xingray.mvp.MvpView;
import com.xingray.mvp.PresenterHolder;

/**
 * @author : leixing
 * @date : 2017-04-20
 * Email       : leixing1012@qq.com
 * Version     : 0.0.1
 * <p>
 * Description : xxx
 */

public abstract class BaseMvpFragment<P> extends Fragment implements MvpView<P> {

    @SuppressWarnings("FieldCanBeLocal")
    private View mRootView;

    protected Activity mActivity;
    protected Context mContext;

    private PresenterHolder<P> mPresenterHolder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        mContext = getContext();
        mPresenterHolder = new PresenterHolder<>();

        initVariables(getArguments());

        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }

        mPresenterHolder.notifyLifeCycleChanged(LifeCycle.CREATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = initView(inflater, container);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenterHolder.notifyLifeCycleChanged(LifeCycle.START);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenterHolder.notifyLifeCycleChanged(LifeCycle.RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenterHolder.notifyLifeCycleChanged(LifeCycle.PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenterHolder.notifyLifeCycleChanged(LifeCycle.STOP);
    }

    @Override
    public void onDestroy() {
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
     * 初始化变量
     *
     * @param arguments 外部传入的参数
     */
    protected abstract void initVariables(Bundle arguments);

    /**
     * 恢复保存的状态
     *
     * @param state 状态数据
     */
    @SuppressWarnings("unused")
    protected void restoreState(Bundle state) {

    }

    /**
     * 初始化视图
     *
     * @param inflater  inflater
     * @param container container
     * @return 加载的视图
     */
    protected abstract View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    /**
     * 加载数据
     */
    protected abstract void loadData();
}
