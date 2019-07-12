package com.xingray.sample.page.list;

import com.xingray.mvp.MvpPresenter;
import com.xingray.mvp.MvpView;
import com.xingray.sample.data.Student;

import java.util.List;

/**
 * xxx
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/7/11 11:48
 */
public interface StudentListContract {
    interface View extends MvpView<Presenter> {

        void showLoading();

        void dismissLoading();

        void showStudents(List<Student> list);

        void scrollTo(int position);
    }

    interface Presenter extends MvpPresenter<View> {

        void loadData();

        void onStop();
    }
}
