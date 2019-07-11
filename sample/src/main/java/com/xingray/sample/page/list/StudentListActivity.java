package com.xingray.sample.page.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xingray.recycleradapter.ItemClickListener;
import com.xingray.recycleradapter.RecyclerAdapter;
import com.xingray.sample.R;
import com.xingray.sample.base.BaseMvpActivity;
import com.xingray.sample.data.Student;
import com.xingray.sample.ui.ProgressDialog;
import com.xingray.sample.ui.ViewUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * xxx
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing@baidu.com
 * @date : 2019/7/11 11:38
 */
public class StudentListActivity extends BaseMvpActivity<StudentListContract.Presenter> implements StudentListContract.View {

    private RecyclerView rvList;
    private StudentListPresenter mPresenter;
    private ProgressDialog mProgressDialog;
    private RecyclerAdapter mAdapter;

    public static void start(Context context) {
        Intent starter = new Intent(context, StudentListActivity.class);
        if (!(context instanceof Activity)) {
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(starter);
    }

    @Override
    protected void initVariables() {
        setPresenterInterface(StudentListContract.Presenter.class);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_student_list);
        rvList = findViewById(R.id.rv_list);

        findViewById(R.id.bt_presenter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter = new StudentListPresenter();
                mPresenter.bindView(StudentListActivity.this);
                bindPresenter(mPresenter);
            }
        });

        findViewById(R.id.bt_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().onStop();
            }
        });

        initList();
    }

    @Override
    protected void loadData() {
        getPresenter().loadData();
    }

    @Override
    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.show();
    }

    @Override
    public void dismissLoading() {
        if (mProgressDialog == null) {
            return;
        }
        mProgressDialog.dismiss();
    }

    @Override
    public void showStudents(List<Student> list) {
        mAdapter.update(list);
    }

    private void initList() {
        rvList.setLayoutManager(new LinearLayoutManager(mContext));

        mAdapter = new RecyclerAdapter(getApplicationContext())
                .typeSupport(Student.class)
                .layoutViewSupport(R.layout.item_student_list)
                .viewHolder(StudentViewHolder.class)
                .itemClickListener(new ItemClickListener<Student>() {
                    @Override
                    public void onItemClick(@NotNull ViewGroup viewGroup, int i, @Nullable Student student) {
                        ViewUtil.showToast(mContext, student.getName());
                    }
                })
                .registerView().registerType();

        rvList.setAdapter(mAdapter);
    }

    @Override
    public void scrollTo(int position) {
        rvList.scrollToPosition(position);
    }
}
