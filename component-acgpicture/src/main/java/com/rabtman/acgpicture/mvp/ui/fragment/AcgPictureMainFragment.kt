package com.rabtman.acgpicture.mvp.ui.fragment

import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.rabtman.acgpicture.R
import com.rabtman.acgpicture.R2
import com.rabtman.acgpicture.mvp.ui.adapter.AcgPictureMainPageAdapter
import com.rabtman.common.base.SimpleFragment
import com.rabtman.router.RouterConstants
import java.util.*

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_PICTURE_MAIN)
class AcgPictureMainFragment : SimpleFragment() {

    @BindView(R2.id.tab_picture)
    internal lateinit var mTabLayout: TabLayout
    @BindView(R2.id.vp_picture)
    internal lateinit var mViewPager: ViewPager

    internal var fragments: MutableList<Fragment> = ArrayList()
    internal lateinit var mAdapter: AcgPictureMainPageAdapter

    override fun getLayoutId(): Int {
        return R.layout.acgpicture_fragment_picture_main
    }

    override fun initData() {
        //animate-picture
        /*val animatePictureFragment = AnimatePictureFragment()
        fragments.add(animatePictureFragment)*/
        //
        val aPictureFragment = APictureFragment()
        fragments.add(aPictureFragment)

        mAdapter = AcgPictureMainPageAdapter(fragmentManager, fragments)
        mViewPager.adapter = mAdapter
        mViewPager.offscreenPageLimit = 1
        mTabLayout.setupWithViewPager(mViewPager)
    }
}
