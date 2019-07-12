package com.xingray.sample.page.main;

import android.view.View;
import android.widget.TextView;

import com.xingray.recycleradapter.BaseViewHolder;
import com.xingray.sample.R;

/**
 * xxx
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing1012@qq.com
 * @date : 2019/7/11 16:39
 */
public class TestViewHolder extends BaseViewHolder<Test> {

    TextView tvName;

    public TestViewHolder(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tv_name);
    }

    @Override
    public void bindItemView(Test test, int i) {
        tvName.setText(test.getName());
    }
}
