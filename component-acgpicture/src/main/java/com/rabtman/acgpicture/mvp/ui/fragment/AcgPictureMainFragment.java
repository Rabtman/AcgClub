package com.rabtman.acgpicture.mvp.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.rabtman.acgpicture.R;
import com.rabtman.acgpicture.R2;
import com.rabtman.acgpicture.mvp.ui.adapter.AcgPictureMainPageAdapter;
import com.rabtman.common.base.SimpleFragment;
import com.rabtman.router.RouterConstants;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_ACGNEWS_MAIN)
public class AcgPictureMainFragment extends SimpleFragment {

  @BindView(R2.id.tab_picture)
  TabLayout mTabLayout;
  @BindView(R2.id.vp_picture)
  ViewPager mViewPager;

  List<Fragment> fragments = new ArrayList<>();
  AcgPictureMainPageAdapter mAdapter;

  @Override
  protected int getLayoutId() {
    return R.layout.acgpicture_fragment_picture_main;
  }

  @Override
  protected void initData() {
    //animate-picture
    AnimatePictureFragment zeroFiveFragment = new AnimatePictureFragment();
    fragments.add(zeroFiveFragment);

    mAdapter = new AcgPictureMainPageAdapter(getFragmentManager(), fragments);
    mViewPager.setAdapter(mAdapter);
    mViewPager.setOffscreenPageLimit(1);
    mTabLayout.setupWithViewPager(mViewPager);
  }
}
