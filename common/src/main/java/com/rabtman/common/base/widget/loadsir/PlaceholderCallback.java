package com.rabtman.common.base.widget.loadsir;

import android.content.Context;
import android.view.View;
import com.kingja.loadsir.callback.Callback;
import com.rabtman.common.R;

public class PlaceholderCallback extends Callback {

  @Override
  protected int onCreateView() {
    return R.layout.view_placeholder_page;
  }

  @Override
  protected boolean onReloadEvent(Context context, View view) {
    return true;
  }
}
