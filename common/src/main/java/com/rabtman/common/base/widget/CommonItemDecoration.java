package com.rabtman.common.base.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.rabtman.common.utils.SizeUtils;

public class CommonItemDecoration extends RecyclerView.ItemDecoration {

  public static final int UNIT_DP = 0;
  public static final int UNIT_PX = 1;
  private int distance;
  private int unit;

  public CommonItemDecoration(int distance, int unit) {
    this.distance = distance;
    this.unit = unit;
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    super.getItemOffsets(outRect, view, parent, state);
    int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
    if (position > -1) {
      if (unit == UNIT_DP) {
        outRect.set(0, SizeUtils.dp2px(distance), 0, 0);
      } else {
        outRect.set(0, distance, 0, 0);
      }
    }
  }
}