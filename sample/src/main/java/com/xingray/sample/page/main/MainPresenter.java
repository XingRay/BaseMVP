package com.xingray.sample.page.main;

import com.xingray.mvp.Presenter;
import com.xingray.sample.lib.TaskExecutor;

import java.util.ArrayList;
import java.util.List;

/**
 * xxx
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/7/10 20:01
 */
public class MainPresenter extends Presenter<MainContract.View> implements MainContract.Presenter {

    MainPresenter() {
        super(MainContract.View.class);
    }

    @Override
    public void loadData() {
        getView().showLoading();
        TaskExecutor.io(new Runnable() {
            @Override
            public void run() {
                final List<Test> tests = loadTestList();
                TaskExecutor.ui(new Runnable() {
                    @Override
                    public void run() {
                        getView().dismissLoading();
                        getView().showTestList(tests);
                    }
                });
            }
        });
    }

    private List<Test> loadTestList() {
        List<Test> testList = new ArrayList<>();

        testList.add(new Test("student list test", "1"));

        return testList;
    }
}
