package com.rabtman.acgnews.mvp.ui.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar.OnMenuItemClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.BindView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rabtman.acgnews.R;
import com.rabtman.acgnews.R2;
import com.rabtman.acgnews.base.constant.IntentConstant;
import com.rabtman.acgnews.di.component.DaggerAcgNewsDetailComponent;
import com.rabtman.acgnews.di.module.AcgNewsDetailModule;
import com.rabtman.acgnews.mvp.contract.AcgNewsDetailContract.View;
import com.rabtman.acgnews.mvp.model.jsoup.AcgNews;
import com.rabtman.acgnews.mvp.model.jsoup.AcgNewsDetail;
import com.rabtman.acgnews.mvp.presenter.AcgNewsDetailPresenter;
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
public class AcgInfoDetailActivity extends BaseActivity<AcgNewsDetailPresenter> implements
    View {

  @BindView(R2.id.toolbar)
  Toolbar mToolBar;
  @BindView(R2.id.tv_acg_detail_content)
  TextView tvAcgDetailContent;
  @BindView(R2.id.tv_acg_detail_title)
  TextView tvAcgDetailTitle;
  @BindView(R2.id.tv_acg_detail_labels)
  TextView tvAcgDetailLabels;
  @BindView(R2.id.tv_acg_detail_datetime)
  TextView tvAcgDetailDatetime;
  private AcgNews mAcgNewsItem;

  @Override
  protected void setupActivityComponent(AppComponent appComponent) {
    DaggerAcgNewsDetailComponent.builder()
        .appComponent(appComponent)
        .acgNewsDetailModule(new AcgNewsDetailModule(this))
        .build()
        .inject(this);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.acgnews_activity_acginfo_detail;
  }

  @Override
  protected void initData() {
    setToolBar(mToolBar, "");
    mAcgNewsItem = getIntent().getParcelableExtra(IntentConstant.ACG_NEWS_DETAIL_ITEM);
    if (mAcgNewsItem == null) {
      showError(R.string.msg_error_unknown);
      return;
    }

    mToolBar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
      @Override
      public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.acginfo_share) {
          mPresenter.start2Share(new RxPermissions(AcgInfoDetailActivity.this));
        }
        return false;
      }
    });

    mPresenter.getNewsDetail(mAcgNewsItem.getContentLink());
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
  public void showNewsDetail(AcgNewsDetail acgNewsDetail) {
    tvAcgDetailTitle.setText(mAcgNewsItem.getTitle());
    tvAcgDetailDatetime
        .setText(getIntent().getStringExtra(mAcgNewsItem.getDateTime()));
    //文章来源信息
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i <= 3; i++) {
      String text = acgNewsDetail.getLabels().get(i);
      stringBuilder.append(text);
      stringBuilder.append("  ");
    }
    tvAcgDetailLabels.setText(stringBuilder.toString());
    //文章内容
    RichText.fromHtml(acgNewsDetail.getContent())
        .into(tvAcgDetailContent);
  }

  @Override
  public void showShareView() {
    UMWeb umWeb = new UMWeb(mAcgNewsItem.getContentLink());
    umWeb.setThumb(new UMImage(this, mAcgNewsItem.getImgUrl()));
    umWeb.setTitle(mAcgNewsItem.getTitle());
    umWeb.setDescription(mAcgNewsItem.getDescription());

    //分享面板ui
    ShareBoardConfig config = new ShareBoardConfig();
    config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
    config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR); // 圆角背景

    new ShareAction(AcgInfoDetailActivity.this)
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
