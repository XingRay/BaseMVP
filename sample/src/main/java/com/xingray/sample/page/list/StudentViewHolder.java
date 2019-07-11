package com.xingray.sample.page.list;

import android.view.View;
import android.widget.TextView;
import com.xingray.recycleradapter.BaseViewHolder;
import com.xingray.sample.R;
import com.xingray.sample.data.Student;
import org.jetbrains.annotations.NotNull;

/**
 * xxx
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing@baidu.com
 * @date : 2019/7/11 17:40
 */
public class StudentViewHolder extends BaseViewHolder<Student> {

    private TextView tvName;

    public StudentViewHolder(@NotNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tv_name);
    }

    @Override
    public void bindItemView(Student student, int i) {
        tvName.setText(student.getName());
    }
}
