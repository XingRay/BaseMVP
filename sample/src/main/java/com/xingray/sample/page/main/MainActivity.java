package com.xingray.sample.page.main;

import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xingray.recycleradapter.ItemClickListener;
import com.xingray.recycleradapter.RecyclerAdapter;
import com.xingray.sample.R;
import com.xingray.sample.base.BaseMvpActivity;
import com.xingray.sample.page.list.StudentListActivity;
import com.xingray.sample.ui.ProgressDialog;

import java.util.List;

/**
 * @author leixing
 */
public class MainActivity extends BaseMvpActivity<MainContract.Presenter> implements MainContract.View {

    private RecyclerView rvList;
    private RecyclerAdapter mAdapter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void initVariables() {
        setPresenterInterface(MainContract.Presenter.class);
        MainPresenter p = new MainPresenter();
        bindPresenter(p);
        p.bindView(this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);

        rvList = findViewById(R.id.rv_list);
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
    public void showTestList(List<Test> tests) {
        mAdapter.update(tests);
    }

    private void initList() {
        rvList.setLayoutManager(new LinearLayoutManager(mContext));

        mAdapter = new RecyclerAdapter(getApplicationContext())
                .typeSupport(Test.class)
                .layoutViewSupport(R.layout.item_test_list)
                .viewHolder(TestViewHolder.class)
                .itemClickListener(new ItemClickListener<Test>() {
                    @Override
                    public void onItemClick(ViewGroup viewGroup, int i, Test test) {
                        gotoTestPage(test);
                    }
                })
                .registerView().registerType();

        rvList.setAdapter(mAdapter);
    }

    private void gotoTestPage(Test test) {
        switch (test.getId()) {
            case "1":
                StudentListActivity.start(this);
                break;
            default:
        }
    }
}
