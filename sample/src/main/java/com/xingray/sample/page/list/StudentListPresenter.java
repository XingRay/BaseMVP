package com.xingray.sample.page.list;

import com.xingray.mvp.AddStrategy;
import com.xingray.mvp.LifeCycle;
import com.xingray.mvp.Presenter;
import com.xingray.sample.data.Student;
import com.xingray.sample.data.StudentMockDataSource;
import com.xingray.sample.lib.TaskExecutor;

import java.util.List;

/**
 * xxx
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/7/11 11:51
 */
public class StudentListPresenter extends Presenter<StudentListContract.View> implements StudentListContract.Presenter {

    private final StudentMockDataSource mDataSource;

    StudentListPresenter() {
        super(StudentListContract.View.class);
        mDataSource = new StudentMockDataSource();
    }

    @Override
    public void loadData() {
        getView().showLoading();

        TaskExecutor.io(new Runnable() {
            @Override
            public void run() {
                final List<Student> list = mDataSource.loadStudents();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TaskExecutor.ui(new Runnable() {
                    @Override
                    public void run() {
                        getView().dismissLoading();
                        getView().showStudents(list);
                    }
                });
            }
        });
    }

    @Override
    public void onStop() {
        runOnLifeCycles(AddStrategy.INSERT_TAIL, LifeCycle.STOP).scrollTo(0);
    }
}
