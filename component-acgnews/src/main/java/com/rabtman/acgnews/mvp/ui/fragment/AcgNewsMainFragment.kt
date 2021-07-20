package com.rabtman.acgnews.mvp.ui.fragment

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.rabtman.acgnews.R
import com.rabtman.acgnews.R2
import com.rabtman.acgnews.mvp.ui.adapter.AcgNewsMainPageAdapter
import com.rabtman.business.router.RouterConstants
import com.rabtman.common.base.SimpleFragment
import java.util.*

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_ACGNEWS_MAIN)
class AcgNewsMainFragment : SimpleFragment() {

    @BindView(R2.id.tab_news)
    lateinit var mTabLayout: TabLayout


    @BindView(R2.id.vp_news)
    lateinit var mViewPager: ViewPager
    var fragments: MutableList<Fragment> = ArrayList()
    var mAdapter: AcgNewsMainPageAdapter? = null
    override fun getLayoutId(): Int {
        return R.layout.acgnews_fragment_news_main
    }

    override fun initData() {
        val zeroFiveFragment = ZeroFiveNewsFragment()
        fragments.add(zeroFiveFragment)
        val ishNewsFragment = ISHNewsFragment()
        fragments.add(ishNewsFragment)
        mAdapter = fragmentManager?.let { AcgNewsMainPageAdapter(it, fragments) }
        mViewPager.adapter = mAdapter
        mViewPager.offscreenPageLimit = 1
        mTabLayout.setupWithViewPager(mViewPager)
    }
}