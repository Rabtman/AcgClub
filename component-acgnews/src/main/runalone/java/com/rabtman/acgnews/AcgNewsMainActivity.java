package com.rabtman.acgnews;

import android.os.Bundle;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rabtman.acgnews.mvp.ui.fragment.AcgNewsMainFragment;
import com.rabtman.common.base.SimpleActivity;

/**
 * @author Rabtman
 */

public class AcgNewsMainActivity extends SimpleActivity {

  @BindView(R.id.layout_load_fragment)
  FrameLayout layoutLoadFragment;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_runalone;
  }

  @Override
  protected void initData() {
    AcgNewsMainFragment acgNewsMainFragment = new AcgNewsMainFragment();
    loadRootFragment(R.id.layout_load_fragment, acgNewsMainFragment);
  }
}
