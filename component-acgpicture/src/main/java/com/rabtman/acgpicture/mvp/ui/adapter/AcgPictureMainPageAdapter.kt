package com.rabtman.acgpicture.mvp.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import com.rabtman.acgpicture.base.constant.SystemConstant

/**
 * @author rabtman
 */

class AcgPictureMainPageAdapter(fragmentManager: FragmentManager,
                                private val fragments: List<Fragment>) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return SystemConstant.ACG_PICTURE_TITLE[position]
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any) {
        //super.destroyItem(container, position, object);
    }
}
