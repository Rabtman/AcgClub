package com.rabtman.acgnews.mvp.ui.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rabtman.acgnews.base.constant.SystemConstant

/**
 * @author zjm
 * @Description:
 * @date 2016/11/23
 */
class AcgNewsMainPageAdapter(fragmentManager: FragmentManager,
                             private val fragments: List<Fragment>) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return SystemConstant.ACG_NEWS_TITLE[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, object);
    }

}