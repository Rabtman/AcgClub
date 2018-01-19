package com.rabtman.acgschedule;

import android.widget.FrameLayout;
import butterknife.BindView;
import com.rabtman.acgschedule.mvp.ui.fragment.ScheduleMainFragment;
import com.rabtman.common.base.SimpleActivity;

/**
 * @author Rabtman
 */

public class AcgScheduleMainActivity extends SimpleActivity {

  @BindView(R.id.layout_load_fragment)
  FrameLayout layoutLoadFragment;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_runalone;
  }

  @Override
  protected void initData() {
    ScheduleMainFragment scheduleMainFragment = new ScheduleMainFragment();
    loadRootFragment(R.id.layout_load_fragment, scheduleMainFragment);
  }
}
