package com.xingray.sample.ui;

import android.content.Context;
import android.widget.Toast;

/**
 * xxx
 *
 * @author : leixing
 * @version : 1.0.0
 * mail : leixing@baidu.com
 * @date : 2019/7/11 17:42
 */
public class ViewUtil {
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
