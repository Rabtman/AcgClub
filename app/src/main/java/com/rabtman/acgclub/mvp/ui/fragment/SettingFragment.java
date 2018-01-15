package com.rabtman.acgclub.mvp.ui.fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.hss01248.dialog.StyledDialog;
import com.rabtman.acgclub.BuildConfig;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.mvp.ui.activity.MainActivity;
import com.rabtman.common.base.SimpleFragment;
import com.rabtman.router.RouterConstants;

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_SETTING)
public class SettingFragment extends SimpleFragment {

  @BindView(R.id.tv_setting_version)
  TextView tvSettingVersion;
  @BindView(R.id.tv_setting_opinion)
  TextView tvSettingOpinion;
  @BindView(R.id.tv_setting_update)
  TextView tvSettingUpdate;
  @BindView(R.id.tv_setting_about)
  TextView tvSettingAbout;


  @Override
  protected int getLayoutId() {
    return R.layout.fragment_setting;
  }

  @Override
  protected void initData() {
    tvSettingVersion.setText("v" + BuildConfig.VERSION_NAME);
  }

  @OnClick({R.id.tv_setting_update, R.id.tv_setting_opinion, R.id.tv_setting_about})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.tv_setting_opinion:
        FeedbackAPI.openFeedbackActivity();
        break;
      case R.id.tv_setting_update:
        if (getActivity() instanceof MainActivity) {
          MainActivity activity = (MainActivity) getActivity();
          activity.getAppVersionInfo(true);
        }
        break;
      case R.id.tv_setting_about:
        StyledDialog.buildCustom(
            LayoutInflater.from(
                getContext()).inflate(R.layout.view_setting_about, null),
            Gravity.CENTER)
            .setCancelable(true, true)
            .show();
        break;
    }
  }
}
