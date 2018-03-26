package com.rabtman.acgclub.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.hss01248.dialog.StyledDialog;
import com.rabtman.acgclub.BuildConfig;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.IntentConstant;
import com.rabtman.acgclub.service.UpdateAppService;
import com.rabtman.common.base.SimpleActivity;
import com.rabtman.router.RouterConstants;

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_SETTING)
public class SettingActivity extends SimpleActivity {

  @BindView(R.id.toolbar)
  Toolbar mToolBar;
  @BindView(R.id.tv_setting_version)
  TextView tvSettingVersion;
  @BindView(R.id.tv_setting_opinion)
  TextView tvSettingOpinion;
  @BindView(R.id.layout_setting_update)
  RelativeLayout tvSettingUpdate;
  @BindView(R.id.tv_setting_about)
  TextView tvSettingAbout;


  @Override
  protected int getLayoutId() {
    return R.layout.activity_setting;
  }

  @Override
  protected void initData() {
    setToolBar(mToolBar, getString(R.string.nav_setting));
    tvSettingVersion.setText("当前版本号 v" + BuildConfig.VERSION_NAME);
  }

  @OnClick({R.id.layout_setting_update, R.id.tv_setting_opinion, R.id.tv_setting_about})
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.tv_setting_opinion:
        FeedbackAPI.openFeedbackActivity();
        break;
      case R.id.layout_setting_update:
        Intent updateAppIntent = new Intent(this, UpdateAppService.class);
        updateAppIntent.putExtra(IntentConstant.CHECK_APP_UPDATE_MANUAL, true);
        break;
      case R.id.tv_setting_about:
        StyledDialog.buildCustom(
            LayoutInflater.from(
                this).inflate(R.layout.view_setting_about, null),
            Gravity.CENTER)
            .setCancelable(true, true)
            .show();
        break;
    }
  }
}
