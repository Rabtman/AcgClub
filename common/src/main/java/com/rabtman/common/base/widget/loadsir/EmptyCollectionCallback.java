package com.rabtman.common.base.widget.loadsir;

import com.kingja.loadsir.callback.Callback;
import com.rabtman.common.R;

public class EmptyCollectionCallback extends Callback {

  @Override
  protected int onCreateView() {
    return R.layout.view_empty_collection;
  }
}
