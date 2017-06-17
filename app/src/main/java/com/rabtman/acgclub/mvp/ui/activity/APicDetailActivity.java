package com.rabtman.acgclub.mvp.ui.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.IntentConstant;
import com.rabtman.acgclub.di.component.DaggerAPicDetailComponent;
import com.rabtman.acgclub.di.module.APicDetailModule;
import com.rabtman.acgclub.mvp.contract.APicDetailContract.View;
import com.rabtman.acgclub.mvp.model.jsoup.APicDetail;
import com.rabtman.acgclub.mvp.presenter.APicDetailPresenter;
import com.rabtman.acgclub.mvp.ui.adapter.APicPagerAdapter;
import com.rabtman.acgclub.mvp.ui.adapter.APicPagerAdapter.PinchImageViewListener;
import com.rabtman.common.base.BaseActivity;
import com.rabtman.common.di.component.AppComponent;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.util.List;
import zlc.season.rxdownload2.RxDownload;

/**
 * @author Rabtman
 */
public class APicDetailActivity extends BaseActivity<APicDetailPresenter> implements
    View {

  @BindView(R.id.toolbar)
  Toolbar mToolBar;
  @BindView(R.id.vp_apic)
  ViewPager vpApic;
  @BindView(R.id.tv_pos)
  TextView tvPos;
  @BindView(R.id.tv_divider)
  TextView tvDivider;
  @BindView(R.id.tv_count)
  TextView tvCount;
  @BindView(R.id.img_download)
  ImageView imgDownload;
  @BindView(R.id.bottom_layout)
  RelativeLayout bottomLayout;

  private int curPos = 0;
  private APicPagerAdapter mAdapter;
  private AnimatorSet showAnimator;
  private AnimatorSet hideAnimator;

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
    setToolBar(mToolBar, getIntent().getStringExtra(IntentConstant.APIC_DETAIL_TITLE));
    initAnimator();
    mPresenter.getAPicDetail(getIntent().getStringExtra(IntentConstant.APIC_DETAIL_URL));
  }

  @Override
  public void showAPictures(APicDetail aPicDetail) {
    List<String> picList = aPicDetail.getPicList();
    tvPos.setText("1");
    tvDivider.setVisibility(android.view.View.VISIBLE);
    tvCount.setText(String.valueOf(picList.size()));
    imgDownload.setVisibility(android.view.View.VISIBLE);

    mAdapter = new APicPagerAdapter(this, picList);
    mAdapter.setPinchImageViewListener(new PinchImageViewListener() {
      @Override
      public void onClick(android.view.View v) {
        if (mToolBar.getVisibility() == android.view.View.VISIBLE) {
          if (hideAnimator != null) {
            hideAnimator.start();
          }
        } else {
          if (showAnimator != null) {
            showAnimator.start();
          }
        }
      }
    });
    vpApic.setOffscreenPageLimit(3);
    vpApic.setAdapter(mAdapter);
    vpApic.addOnPageChangeListener(new OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {
        curPos = position;
        tvPos.setText(String.valueOf(position + 1));
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });
  }

  @OnClick(R.id.img_download)
  void downloadPicture() {
    if (mAdapter != null) {
      mPresenter
          .downloadPicture(new RxPermissions(this), RxDownload.getInstance(this),
              mAdapter.getApicList().get(curPos));
    }
  }

  private void initAnimator() {
    //hide view
    if (hideAnimator == null) {
      hideAnimator = new AnimatorSet();
      ObjectAnimator toolBarAnimator = ObjectAnimator
          .ofFloat(mToolBar, "translationY", -(mToolBar.getHeight()));
      toolBarAnimator.setDuration(500);
      toolBarAnimator.addListener(new AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
          mToolBar.setVisibility(android.view.View.INVISIBLE);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
      });
      ObjectAnimator bottomAnimator = ObjectAnimator
          .ofFloat(bottomLayout, "translationY", bottomLayout.getHeight());
      bottomAnimator.setDuration(500);
      bottomAnimator.addListener(new AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
          bottomLayout.setVisibility(android.view.View.INVISIBLE);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
      });
      hideAnimator.playTogether(toolBarAnimator, bottomAnimator);
    }
    //show view
    if (showAnimator == null) {
      showAnimator = new AnimatorSet();
      ObjectAnimator toolBarAnimator = ObjectAnimator.ofFloat(mToolBar, "translationY", 0);
      toolBarAnimator.setDuration(500);
      toolBarAnimator.addListener(new AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
          mToolBar.setVisibility(android.view.View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animator animation) {

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
      });
      ObjectAnimator bottomAnimator = ObjectAnimator.ofFloat(bottomLayout, "translationY", 0);
      bottomAnimator.setDuration(500);
      bottomAnimator.addListener(new AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
          bottomLayout.setVisibility(android.view.View.VISIBLE);
        }

        @Override
        public void onAnimationEnd(Animator animation) {
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
      });
      showAnimator.playTogether(toolBarAnimator, bottomAnimator);
    }
  }

}
