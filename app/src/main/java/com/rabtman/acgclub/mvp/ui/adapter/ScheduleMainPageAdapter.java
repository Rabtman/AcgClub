package com.rabtman.acgclub.mvp.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;
import java.util.List;

/**
 * @author zjm
 * @Description:
 * @date 2016/11/23
 */

public class ScheduleMainPageAdapter extends FragmentPagerAdapter {

  private List<String> weekName;
  private List<Fragment> fragments;

  public ScheduleMainPageAdapter(FragmentManager fragmentManager, List<String> weekName,
      List<Fragment> fragments) {
    super(fragmentManager);
    this.weekName = weekName;
    this.fragments = fragments;
  }

  @Override
  public Fragment getItem(int position) {
    return fragments.get(position);
  }

  @Override
  public int getCount() {
    return fragments.size();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return weekName.get(position);
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    //super.destroyItem(container, position, object);
  }
}
