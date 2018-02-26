package com.rabtman.acgcomic

import android.widget.FrameLayout
import butterknife.BindView
import com.rabtman.acgcomic.mvp.ui.fragment.AcgComicMainFragment
import com.rabtman.common.base.SimpleActivity

/**
 * @author Rabtman
 */

class AcgComicMainActivity : SimpleActivity() {

    @BindView(R.id.layout_load_fragment)
    lateinit var layoutLoadFragment: FrameLayout

    override fun getLayoutId(): Int {
        return R.layout.activity_runalone
    }

    override fun initData() {
        val comicMainFragment = AcgComicMainFragment()
        loadRootFragment(R.id.layout_load_fragment, comicMainFragment)
    }
}
