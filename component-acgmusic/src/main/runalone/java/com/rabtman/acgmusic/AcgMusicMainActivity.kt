package com.rabtman.acgmusic

import android.widget.FrameLayout
import butterknife.BindView
import com.rabtman.acgmusic.mvp.ui.fragment.AcgMusicFragment
import com.rabtman.common.base.SimpleActivity

/**
 * @author Rabtman
 */

class AcgMusicMainActivity : SimpleActivity() {

    @BindView(R2.id.layout_load_fragment)
    lateinit var layoutLoadFragment: FrameLayout

    override fun getLayoutId(): Int {
        return R.layout.activity_runalone
    }

    override fun initData() {
        val musicFragment = AcgMusicFragment()
        loadRootFragment(R.id.layout_load_fragment, musicFragment)
    }
}
