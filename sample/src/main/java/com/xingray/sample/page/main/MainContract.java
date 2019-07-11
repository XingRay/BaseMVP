package com.xingray.sample.page.main;

import com.xingray.mvp.MvpPresenter;
import com.xingray.mvp.MvpView;

import java.util.List;

/**
 * xxx
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing@baidu.com
 * @date : 2019/7/10 20:00
 */
public interface MainContract {
    interface View extends MvpView<Presenter> {

        void showLoading();

        void dismissLoading();

        void showTestList(List<Test> tests);
    }

    interface Presenter extends MvpPresenter<View> {

        void loadData();
    }
}
