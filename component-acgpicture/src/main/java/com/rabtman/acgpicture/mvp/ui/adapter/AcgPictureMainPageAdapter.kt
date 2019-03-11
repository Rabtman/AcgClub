package com.rabtman.acgpicture.mvp.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import com.rabtman.acgpicture.mvp.model.entity.AcgPictureType

/**
 * @author rabtman
 */

class AcgPictureMainPageAdapter(fragmentManager: FragmentManager,
                                private val fragments: List<Fragment>) : FragmentPagerAdapter(fragmentManager) {

    private var types: List<AcgPictureType> = listOf()

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        if (types.isEmpty()) {
            return ""
        } else {
            return types.get(position).name
        }
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any) {
        //super.destroyItem(container, position, object);
    }

    fun setPictureTypes(types: List<AcgPictureType>) {
        this.types = types
    }
}
