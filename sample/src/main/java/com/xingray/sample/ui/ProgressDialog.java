package com.xingray.sample.ui;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import com.xingray.sample.R;


/**
 * 显示加载中的dialog
 *
 * @author : leixing
 * @date : 2018/7/27 19:39
 * <p>
 * description : xxx
 */
public class ProgressDialog extends Dialog {

    private TextView tvContent;
    private ImageView ivLoading;

    public ProgressDialog(Context context) {
        super(context);

        setContentView(R.layout.dialog_progress);
    }
}
