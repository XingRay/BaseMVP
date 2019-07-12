package com.xingray.mvp;

/**
 * xxx
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/7/11 11:19
 */
public interface MvpView<P> extends LifeCycleProvider {

    void setPresenterInterface(Class<P> cls);

    boolean hasPresenter();

    void bindPresenter(P p);

    P getPresenter();
}
