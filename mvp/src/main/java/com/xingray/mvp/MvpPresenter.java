package com.xingray.mvp;

/**
 * xxx
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/7/11 11:25
 */
public interface MvpPresenter<V extends LifeCycleProvider> {

    void bindView(V view);
}
