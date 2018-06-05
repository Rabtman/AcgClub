package com.rabtman.acgnews.mvp.ui.activity;

import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.BindView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rabtman.acgnews.R;
import com.rabtman.acgnews.R2;
import com.rabtman.acgnews.base.constant.HtmlConstant;
import com.rabtman.acgnews.base.constant.IntentConstant;
import com.rabtman.acgnews.di.component.DaggerISHNewsDetailComponent;
import com.rabtman.acgnews.di.module.ISHNewsDetailModule;
import com.rabtman.acgnews.mvp.contract.ISHNewsDetailContract.View;
import com.rabtman.acgnews.mvp.model.entity.SHPostDetail;
import com.rabtman.acgnews.mvp.model.entity.SHPostItem;
import com.rabtman.acgnews.mvp.presenter.ISHNewsDetailPresenter;
import com.rabtman.common.base.BaseActivity;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.utils.ExceptionUtils;
import com.rabtman.common.utils.LogUtil;
import com.rabtman.router.RouterConstants;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.zzhoujay.richtext.RichText;

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_ACGNEWS_DETAIL)
public class ISHNewsDetailActivity extends BaseActivity<ISHNewsDetailPresenter> implements
    View {

  @BindView(R2.id.toolbar)
  Toolbar mToolBar;
  @BindView(R2.id.layout_content)
  NestedScrollView layoutContent;
  @BindView(R2.id.tv_acg_detail_content)
  TextView tvAcgDetailContent;
  @BindView(R2.id.tv_acg_detail_title)
  TextView tvAcgDetailTitle;
  @BindView(R2.id.tv_acg_detail_labels)
  TextView tvAcgDetailLabels;
  @BindView(R2.id.tv_acg_detail_datetime)
  TextView tvAcgDetailDatetime;
  private SHPostItem mSHNewsPostItem;

  @Override
  protected void setupActivityComponent(AppComponent appComponent) {
    DaggerISHNewsDetailComponent.builder()
        .appComponent(appComponent)
        .iSHNewsDetailModule(new ISHNewsDetailModule(this))
        .build()
        .inject(this);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.acgnews_activity_acginfo_detail;
  }

  @Override
  protected Object registerTarget() {
    return layoutContent;
  }

  @Override
  protected boolean useLoadSir() {
    return true;
  }

  @Override
  protected void onPageRetry(android.view.View v) {
    mPresenter.getNewsDetail(mSHNewsPostItem.getId());
  }

  @Override
  protected void initData() {
    setToolBar(mToolBar, "");
    tvAcgDetailLabels.setVisibility(android.view.View.GONE);
    mSHNewsPostItem = getIntent().getParcelableExtra(IntentConstant.ISH_NEWS_ITEM);
    if (mSHNewsPostItem == null) {
      showError(R.string.msg_error_unknown);
      return;
    }

    mToolBar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.acginfo_share) {
          mPresenter.start2Share(new RxPermissions(ISHNewsDetailActivity.this));
        }
        return false;
      }
    });

    mPresenter.getNewsDetail(mSHNewsPostItem.getId());
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    UMShareAPI.get(this).release();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.acgnews_activity_acginfo_share_menu, menu);
    return true;
  }

  @Override
  public void showNewsDetail(SHPostDetail shPostDetail) {
    tvAcgDetailTitle.setText(shPostDetail.getTitle());
    tvAcgDetailDatetime
        .setText(getIntent().getStringExtra(shPostDetail.getTime()));
    //文章内容
    RichText.fromHtml(
        shPostDetail.getContent()
            .replaceAll("\"/upload", "\"" + HtmlConstant.ISH_IMG_URL)
    )
        .into(tvAcgDetailContent);
  }

  @Override
  public void showShareView() {
    UMWeb umWeb = new UMWeb(
        getString(R.string.acgnews_ish_post_detail_origin_url, mSHNewsPostItem.getId()));
    umWeb.setThumb(
        new UMImage(this, mSHNewsPostItem.getThumb().replace("/upload", HtmlConstant.ISH_IMG_URL)));
    umWeb.setTitle(mSHNewsPostItem.getTitle());

    //分享面板ui
    ShareBoardConfig config = new ShareBoardConfig();
    config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
    config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR); // 圆角背景

    new ShareAction(ISHNewsDetailActivity.this)
        .withMedia(umWeb)
        .setDisplayList(
            SHARE_MEDIA.SINA,
            SHARE_MEDIA.QQ,
            SHARE_MEDIA.QZONE,
            SHARE_MEDIA.WEIXIN,
            SHARE_MEDIA.WEIXIN_CIRCLE,
            SHARE_MEDIA.WEIXIN_FAVORITE,
            SHARE_MEDIA.MORE)
        .setCallback(new UMShareListener() {
          @Override
          public void onStart(SHARE_MEDIA share_media) {

          }

          @Override
          public void onResult(SHARE_MEDIA share_media) {
            LogUtil.d("share media:" + share_media);
            if (share_media != SHARE_MEDIA.MORE) {
              showMsg(R.string.msg_success_umeng_share);
            }
          }

          @Override
          public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            if (share_media != SHARE_MEDIA.MORE) {
              showError(R.string.msg_error_umeng_share);
            }
            if (throwable != null) {
              LogUtil.e(ExceptionUtils.errToStr(throwable));
            }
          }

          @Override
          public void onCancel(SHARE_MEDIA share_media) {

          }
        })
        .open(config);
  }
}
