package com.rabtman.acgclub.mvp.ui.fragment;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.rabtman.acgclub.BuildConfig;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.mvp.ui.activity.MainActivity;
import com.rabtman.common.base.SimpleFragment;

/**
 * @author Rabtman
 */

public class SettingFragment extends SimpleFragment {

  @BindView(R.id.tv_setting_version)
  TextView tvSettingVersion;
  @BindView(R.id.layout_setting_opinion)
  RelativeLayout layoutSettingOpinion;
  @BindView(R.id.layout_setting_update)
  RelativeLayout layoutSettingUpdate;

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_setting;
  }

  @Override
  protected void initData() {
    tvSettingVersion.setText("v" + BuildConfig.VERSION_NAME);
  }

  @OnClick({R.id.layout_setting_opinion, R.id.layout_setting_update})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.layout_setting_opinion:
        FeedbackAPI.openFeedbackActivity();
        break;
      case R.id.layout_setting_update:
        if (getActivity() instanceof MainActivity) {
          MainActivity activity = (MainActivity) getActivity();
          activity.getAppVersionInfo(true);
        }
        break;
    }
  }
}
