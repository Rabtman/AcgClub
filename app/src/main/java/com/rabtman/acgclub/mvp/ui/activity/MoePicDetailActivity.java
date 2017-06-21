package com.rabtman.acgclub.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager.LayoutParams;
import butterknife.BindView;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.IntentConstant;
import com.rabtman.acgclub.base.view.PinchImageView;
import com.rabtman.acgclub.di.component.DaggerAPicDetailComponent;
import com.rabtman.acgclub.di.module.APicDetailModule;
import com.rabtman.acgclub.mvp.contract.APicDetailContract.View;
import com.rabtman.acgclub.mvp.presenter.APicDetailPresenter;
import com.rabtman.common.base.BaseActivity;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.imageloader.glide.GlideImageConfig;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.io.File;
import java.io.FileNotFoundException;
import zlc.season.rxdownload2.RxDownload;

/**
 * @author Rabtman
 */
public class MoePicDetailActivity extends BaseActivity<APicDetailPresenter> implements
    View {

  @BindView(R.id.toolbar)
  Toolbar mToolBar;
  @BindView(R.id.img_moe)
  PinchImageView imgMoe;

  private RxPermissions rxPermissions;
  private String curImgUrl;

  @Override
  protected void setupActivityComponent(AppComponent appComponent) {
    DaggerAPicDetailComponent.builder()
        .appComponent(appComponent)
        .aPicDetailModule(new APicDetailModule(this))
        .build()
        .inject(this);
  }

  @Override
  protected int getLayout() {
    getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN,
        LayoutParams.FLAG_FULLSCREEN);
    return R.layout.activity_apic_detail;
  }

  @Override
  protected void initData() {
    setToolBar(mToolBar, "");
    rxPermissions = new RxPermissions(this);
    curImgUrl = getIntent().getStringExtra(IntentConstant.MOE_PIC_URL);
    showPicture(curImgUrl);
  }

  @Override
  public void showPicture(String picUrl) {
    getAppComponent().imageLoader()
        .loadImage(this,
            GlideImageConfig
                .builder()
                .url(picUrl)
                .zoomStrategy(GlideImageConfig.FIT_CENTER)
                .imagerView(imgMoe)
                .build());
  }

  @Override
  public void savePictureSuccess(File imgFile) {
    //插入图库
    try {
      MediaStore.Images.Media.insertImage(getContentResolver(),
          imgFile.getAbsolutePath(), imgFile.getName(), null);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    //通知图库更新
    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
    intent.setData(Uri.fromFile(imgFile));
    sendBroadcast(intent);
  }

  void downloadPicture() {
    mPresenter
        .downloadPicture(rxPermissions, RxDownload.getInstance(this),
            curImgUrl.replace("md.", ""));
  }

}
