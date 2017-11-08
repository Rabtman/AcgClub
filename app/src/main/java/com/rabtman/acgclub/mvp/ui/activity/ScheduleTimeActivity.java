package com.rabtman.acgclub.mvp.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener;
import com.rabtman.acgclub.R;
import com.rabtman.acgclub.base.constant.HtmlConstant;
import com.rabtman.acgclub.base.constant.IntentConstant;
import com.rabtman.acgclub.base.constant.SystemConstant;
import com.rabtman.acgclub.mvp.model.entity.ScheduleTimeItem;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleWeek;
import com.rabtman.acgclub.mvp.model.jsoup.ScheduleWeek.ScheduleItem;
import com.rabtman.acgclub.mvp.ui.adapter.ScheduleTimeAdapter;
import com.rabtman.common.base.SimpleActivity;
import com.rabtman.common.utils.DimenUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rabtman
 */


public class ScheduleTimeActivity extends SimpleActivity {


  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.tab_schedule_time)
  TabLayout tabScheduleTime;
  @BindView(R.id.rcv_schedule_time)
  RecyclerView rcvScheduleTime;
  private ScheduleTimeAdapter mAdapter;
  private LinearLayoutManager mLayoutManager;
  private int currentHeaderPosition = 0;
  private SparseIntArray headerArray = new SparseIntArray();

  @Override
  protected int getLayoutId() {
    return R.layout.activity_schedule_time;
  }

  @Override
  protected void initData() {
    setToolBar(toolbar, getString(R.string.title_schedule_new));

    ArrayList<ScheduleWeek> scheduleWeeks = getIntent()
        .getParcelableArrayListExtra(IntentConstant.SCHEDULE_WEEK);
    if (scheduleWeeks == null | scheduleWeeks.size() <= 0) {
      showError(R.string.msg_error_data_null);
      return;
    }

    //将数据源转化为列表展示的格式
    List<ScheduleTimeItem> scheduleTimeItems = new ArrayList<>();
    int headerPos = 0;
    for (int i = 0; i < scheduleWeeks.size(); i++) {
      //tab 标题栏
      Tab tab = tabScheduleTime.newTab().setText(SystemConstant.SCHEDULE_WEEK_TITLE[i]);
      tabScheduleTime.addTab(tab);
      //一周中每天的番剧列表数据合并为用于展示的单一列表
      ScheduleWeek schduleWeek = scheduleWeeks.get(i);
      scheduleTimeItems
          .add(new ScheduleTimeItem(true, SystemConstant.SCHEDULE_WEEK_LIST_TITLE[i], i));
      headerArray.put(i, headerPos);
      headerPos++;
      for (ScheduleItem scheduleItem : schduleWeek.getScheduleItems()) {
        scheduleTimeItems.add(new ScheduleTimeItem(scheduleItem));
        headerPos++;
      }
    }

    //通过反射修改tab下划线样式
    try {
      Class<?> tablayout = tabScheduleTime.getClass();
      Field tabStrip = tablayout.getDeclaredField("mTabStrip");
      tabStrip.setAccessible(true);
      LinearLayout ll_tab = (LinearLayout) tabStrip.get(tabScheduleTime);
      for (int i = 0; i < ll_tab.getChildCount(); i++) {
        View child = ll_tab.getChildAt(i);
        child.setPadding(0, 0, 0, 0);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
            LinearLayout.LayoutParams.MATCH_PARENT, 1);
        params.setMarginStart(DimenUtils.dpToPx(this, 20f));
        params.setMarginEnd(DimenUtils.dpToPx(this, 20f));
        child.setLayoutParams(params);
        child.invalidate();
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }

    tabScheduleTime.addOnTabSelectedListener(new OnTabSelectedListener() {
      @Override
      public void onTabSelected(Tab tab) {
        if (mAdapter.getItemCount() > 0) {
          mLayoutManager
              .scrollToPositionWithOffset(headerArray.get(tab.getPosition()),
                  (int) mLayoutManager.computeScrollVectorForPosition(
                      headerArray.get(tab.getPosition())).y);
        }
      }

      @Override
      public void onTabUnselected(Tab tab) {

      }

      @Override
      public void onTabReselected(Tab tab) {

      }
    });

    mAdapter = new ScheduleTimeAdapter(scheduleTimeItems);
    //item点击事件
    mAdapter.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ScheduleTimeItem scheduleItem = (ScheduleTimeItem) adapter.getItem(position);
        Intent intent = new Intent(getBaseContext(), ScheduleDetailActivity.class);
        if (scheduleItem != null && scheduleItem.t != null) {
          intent.putExtra(IntentConstant.SCHEDULE_DETAIL_URL,
              HtmlConstant.DILIDILI_URL + scheduleItem.t.getAnimeLink());
        }
        startActivity(intent);
      }
    });
    //item子项点击事件
    mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
      @Override
      public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if (view.getId() == R.id.schedule_episode) {
          ScheduleTimeItem scheduleItem = (ScheduleTimeItem) adapter.getItem(position);
          Intent intent = new Intent(getBaseContext(), ScheduleVideoActivity.class);
          if (scheduleItem != null && scheduleItem.t != null) {
            intent.putExtra(IntentConstant.SCHEDULE_EPISODE_URL, scheduleItem.t.getEpisodeLink());
          }
          startActivity(intent);
        }
      }
    });
    mLayoutManager = new LinearLayoutManager(mContext);
    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    rcvScheduleTime.setLayoutManager(mLayoutManager);
    rcvScheduleTime.setAdapter(mAdapter);
    rcvScheduleTime.addOnScrollListener(new OnScrollListener() {
      @Override
      public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int fistVisibleItemPos = mLayoutManager.findFirstVisibleItemPosition();
        if (mAdapter.getItemCount() > 0) {
          ScheduleTimeItem scheduleTimeItem = mAdapter.getItem(fistVisibleItemPos);
          if (scheduleTimeItem != null && scheduleTimeItem.isHeader
              && currentHeaderPosition != fistVisibleItemPos) {
            currentHeaderPosition = fistVisibleItemPos;
            tabScheduleTime.getTabAt(scheduleTimeItem.headerIndex).select();
          }
        }
      }
    });
  }
}
