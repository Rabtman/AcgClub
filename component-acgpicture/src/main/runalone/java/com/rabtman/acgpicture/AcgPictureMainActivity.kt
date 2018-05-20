package com.rabtman.acgpicture

import android.widget.FrameLayout
import butterknife.BindView
import com.rabtman.acgpicture.mvp.ui.fragment.AcgPictureMainFragment
import com.rabtman.common.base.SimpleActivity

/**
 * @author Rabtman
 */

class AcgPictureMainActivity : SimpleActivity() {

    @BindView(R2.id.layout_load_fragment)
    lateinit var layoutLoadFragment: FrameLayout

    override fun getLayoutId(): Int {
        return R.layout.activity_runalone
    }

    override fun initData() {
        val acgPictureMainFragment = AcgPictureMainFragment()
        loadRootFragment(R.id.layout_load_fragment, acgPictureMainFragment)
    }
}
