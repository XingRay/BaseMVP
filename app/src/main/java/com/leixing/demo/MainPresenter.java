package com.leixing.demo;

import android.arch.core.executor.TaskExecutor;

import com.leixing.basemvp.BasePresenter;
import com.leixing.basemvp.LifeCycle;
import com.leixing.basemvp.TaskAddStrategy;

/**
 * xxx
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing@baidu.com
 * @date : 2019/7/10 20:01
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Prrsenter {
    public MainPresenter() {
    }

    void test(){
        TaskExecutor.io(){
            runOnLifeCycles(TaskAddStrategy.OVERRIDE, LifeCycle.RESUME).close();
        }
    }
}
