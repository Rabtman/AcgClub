package com.rabtman.acgschedule.mvp.ui.activity;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.jaeger.library.StatusBarUtil;
import com.kingja.loadsir.callback.Callback.OnReloadListener;
import com.kingja.loadsir.core.LoadSir;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.rabtman.acgschedule.R;
import com.rabtman.acgschedule.R2;
import com.rabtman.acgschedule.base.constant.IntentConstant;
import com.rabtman.acgschedule.di.component.DaggerScheduleDetailComponent;
import com.rabtman.acgschedule.di.module.ScheduleDetailModule;
import com.rabtman.acgschedule.mvp.contract.ScheduleDetailContract.View;
import com.rabtman.acgschedule.mvp.model.entity.ScheduleCache;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleDetail;
import com.rabtman.acgschedule.mvp.model.jsoup.ScheduleDetail.ScheduleEpisode;
import com.rabtman.acgschedule.mvp.presenter.ScheduleDetailPresenter;
import com.rabtman.acgschedule.mvp.ui.adapter.ScheduleDetailEpisodeItemAdapter;
import com.rabtman.common.base.BaseActivity;
import com.rabtman.common.base.widget.loadsir.EmptyCallback;
import com.rabtman.common.base.widget.loadsir.PlaceholderCallback;
import com.rabtman.common.base.widget.loadsir.RetryCallback;
import com.rabtman.common.di.component.AppComponent;
import com.rabtman.common.imageloader.glide.GlideImageConfig;
import com.rabtman.common.imageloader.glide.transformations.BlurTransformation;
import com.rabtman.router.RouterConstants;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.util.List;

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_SCHEDULE_DETAIL)
public class ScheduleDetailActivity extends BaseActivity<ScheduleDetailPresenter> implements
    View {

  @BindView(R2.id.toolbar)
  Toolbar mToolBar;
  @BindView(R2.id.toolbar_title)
  TextView mToolBarTitle;
  @BindView(R2.id.app_bar)
  AppBarLayout appBar;
  @BindView(R2.id.collapsing_toolbar)
  CollapsingToolbarLayout collapsingToolbarLayout;
  @BindView(R2.id.btn_schedule_detail_like)
  ImageView btnScheduleDetailLike;
  @BindView(R2.id.btn_schedule_detail_read)
  CardView btnScheduleDetailRead;
  @BindView(R2.id.tv_schedule_detail_read)
  TextView tvScheduleDetailRead;
  @BindView(R2.id.btn_schedule_detail_more)
  TextView btnScheduleDetailMore;
  @BindView(R2.id.img_schedule_title_bg)
  ImageView imgScheduleTitleBg;
  @BindView(R2.id.img_schedule_detail_icon)
  ImageView imgScheduleDetailIcon;
  @BindView(R2.id.tv_schedule_detail_time)
  TextView tvScheduleDetailTime;
  @BindView(R2.id.tv_schedule_detail_area)
  TextView tvScheduleDetailAera;
  @BindView(R2.id.tv_schedule_detail_proc)
  TextView tvScheduleDetailProc;
  @BindView(R2.id.tv_schedule_detail_label)
  TextView tvScheduleDetailLabel;
  @BindView(R2.id.tv_schedule_detail_description)
  ExpandableTextView tvScheduleDetailDescription;
  @BindView(R2.id.layout_description)
  CardView layoutSceduleDescription;
  @BindView(R2.id.layout_episode)
  CardView layoutSceduleEpisode;
  @BindView(R2.id.rcv_schedule_detail)
  RecyclerView rcvScheduleDetail;
  private ScheduleDetailEpisodeItemAdapter episodeItemAdapter;
  private RxPermissions rxPermissions;

  @Override
  protected void setupActivityComponent(AppComponent appComponent) {
    DaggerScheduleDetailComponent.builder()
        .appComponent(appComponent)
        .scheduleDetailModule(new ScheduleDetailModule(this))
        .build()
        .inject(this);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.acgschedule_activity_schedule_detail;
  }

  @Override
  protected void setStatusBar() {
    StatusBarUtil.setTranslucentForImageView(this, 0, mToolBar);
  }

  @Override
  protected void initData() {
    initPageStatus();
    setToolBar(mToolBar, "");
    collapsingToolbarLayout.setTitleEnabled(false);
    rxPermissions = new RxPermissions(this);

    String scheduleUrl = getIntent().getStringExtra(IntentConstant.SCHEDULE_DETAIL_URL);
    if (TextUtils.isEmpty(scheduleUrl)) {
      showError(R.string.msg_error_url_null);
      return;
    }
    btnScheduleDetailLike.setTag(false);
    mPresenter.setCurrentScheduleUrl(scheduleUrl);
    mPresenter.getScheduleDetail();
    mPresenter.getCurrentScheduleCache(rxPermissions, false);
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  private void initPageStatus() {
    mLoadService = new LoadSir.Builder()
        .addCallback(new PlaceholderCallback())
        .addCallback(new EmptyCallback())
        .addCallback(new RetryCallback())
        .setDefaultCallback(PlaceholderCallback.class)
        .build()
        .register(this, new OnReloadListener() {
          @Override
          public void onReload(android.view.View v) {

          }
        });
  }

  @Override
  public void showScheduleDetail(ScheduleDetail scheduleDetail) {
    mToolBarTitle.setText(scheduleDetail.getScheduleTitle());
    //模糊背景
    mAppComponent.imageLoader().loadImage(mContext,
        GlideImageConfig
            .builder()
            .url(scheduleDetail.getImgUrl())
            .transformation(new BlurTransformation(25, 2))
            .imageView(imgScheduleTitleBg)
            .build()
    );
    //番剧展示图
    mAppComponent.imageLoader().loadImage(mContext,
        GlideImageConfig
            .builder()
            .url(scheduleDetail.getImgUrl())
            .imageView(imgScheduleDetailIcon)
            .build()
    );
    tvScheduleDetailLabel.setText(scheduleDetail.getScheduleLabel());
    tvScheduleDetailTime.setText(scheduleDetail.getScheduleTime());
    if (TextUtils.isEmpty(scheduleDetail.getScheduleProc())) {
      tvScheduleDetailProc.setText(R.string.acgschedule_label_schedule_no_proc);
    } else {
      tvScheduleDetailProc.setText(scheduleDetail.getScheduleProc());
    }
    tvScheduleDetailAera.setText(scheduleDetail.getScheduleAera());
    //番剧介绍
    String desc = scheduleDetail.getDescription();
    if (!TextUtils.isEmpty(desc)) {
      tvScheduleDetailDescription.setText(desc.replace("简介：", ""));
    } else {
      layoutSceduleDescription.setVisibility(android.view.View.GONE);
    }
    //选集
    if (scheduleDetail.getScheduleEpisodes() != null
        && scheduleDetail.getScheduleEpisodes().size() > 1) {
      layoutSceduleEpisode.setVisibility(android.view.View.VISIBLE);
      btnScheduleDetailRead.setVisibility(android.view.View.VISIBLE);
      episodeItemAdapter = new ScheduleDetailEpisodeItemAdapter(
          scheduleDetail.getScheduleEpisodes());
      episodeItemAdapter.setOnItemClickListener(new OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, android.view.View view, int position) {
          final ScheduleEpisode scheduleEpisode = (ScheduleEpisode) adapter.getData().get(position);
          mPresenter.updateScheduleReadRecord(position);
          mPresenter.checkPermission2ScheduleVideo(rxPermissions, scheduleEpisode.getLink());
        }
      });

      GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
      layoutManager.setOrientation(GridLayoutManager.VERTICAL);
      rcvScheduleDetail.setLayoutManager(layoutManager);
      rcvScheduleDetail.setNestedScrollingEnabled(false);
      episodeItemAdapter.bindToRecyclerView(rcvScheduleDetail);

      if (scheduleDetail.getScheduleEpisodes().size()
          > ScheduleDetailEpisodeItemAdapter.DEFAULT_ITEM_COUNT) {
        btnScheduleDetailMore.setVisibility(android.view.View.VISIBLE);
      }
    }
  }

  @OnClick(R2.id.btn_schedule_detail_more)
  public void loadMoreEpisode() {
    episodeItemAdapter.setItemCount();
    btnScheduleDetailMore.setVisibility(android.view.View.GONE);
  }

  @Override
  public void start2ScheduleVideo(String videoUrl) {
    Intent intent = new Intent(getBaseContext(), ScheduleVideoActivity.class);
    intent.putExtra(IntentConstant.SCHEDULE_EPISODE_URL, videoUrl);
    startActivity(intent);
  }

  @OnClick(R2.id.btn_schedule_detail_like)
  public void collectSchedule() {
    Object isCollected = btnScheduleDetailLike.getTag();
    if (isCollected == null) {
      return;
    }
    mPresenter.collectOrCancelSchedule((Boolean) isCollected);
  }

  @OnClick(R2.id.btn_schedule_detail_read)
  public void getNextVideo() {
    mPresenter.getCurrentScheduleCache(rxPermissions, true);
  }

  @Override
  public void showScheduleCacheStatus(ScheduleCache scheduleCache) {
    //是否有收藏
    if ((Boolean) btnScheduleDetailLike.getTag() != scheduleCache.isCollect()) {
      btnScheduleDetailLike.setTag(scheduleCache.isCollect());
      if (scheduleCache.isCollect()) {
        btnScheduleDetailLike
            .setImageDrawable(
                ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_heart_solid));
      } else {
        btnScheduleDetailLike
            .setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_heart));
      }
    }

    //更新上一次观看状态
    if (episodeItemAdapter != null) {
      int lastWatchPos = scheduleCache.getLastWatchPos();
      if (lastWatchPos != -1) {
        List<ScheduleEpisode> scheduleEpisodes = episodeItemAdapter.getData();
        String episodeName;
        if (lastWatchPos + 1 >= scheduleEpisodes.size() - 1) {
          episodeName = scheduleEpisodes.get(scheduleEpisodes.size() - 1).getName();
        } else {
          episodeName = scheduleEpisodes.get(lastWatchPos + 1).getName();
        }
        StringBuilder episodeBuilder = new StringBuilder();
        episodeBuilder.append("续看 ");
        try {
          Integer.parseInt(episodeName);
          episodeBuilder.append("第").append(episodeName).append("话");
        } catch (Exception e) {
          episodeBuilder.append(episodeName);
        }
        tvScheduleDetailRead.setText(episodeBuilder.toString());
      }
      episodeItemAdapter.setRecordPos(lastWatchPos);
    }
  }

}
