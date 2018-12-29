package com.rabtman.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.dovar.dtoast.DToast;
import com.dovar.dtoast.inner.IToast;
import com.rabtman.common.R;

public class ToastUtil {

  public static void show(Context mContext, String msg) {
    if (mContext == null) {
      return;
    }
    if (msg == null) {
      return;
    }
    IToast toast = DToast.make(mContext);
    TextView tv_text = (TextView) toast.getView().findViewById(R.id.tv_content);
    if (tv_text != null) {
      tv_text.setText(msg);
    }
    toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 30).show();
  }


  public static void showAtCenter(Context mContext, String msg) {
    if (mContext == null) {
      return;
    }
    if (msg == null) {
      return;
    }
    View toastRoot = View.inflate(mContext, R.layout.view_toast_center, null);
    TextView tv_text = (TextView) toastRoot.findViewById(R.id.tv_content);
    if (tv_text != null) {
      tv_text.setText(msg);
    }
    DToast.make(mContext)
        .setView(toastRoot)
        .setGravity(Gravity.CENTER, 0, 0)
        .show();
  }

  //退出APP时调用
  public static void cancelAll() {
    DToast.cancel();
  }
}