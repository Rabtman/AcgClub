package com.rabtman.acgnews

import android.widget.FrameLayout
import butterknife.BindView
import com.rabtman.acgnews.mvp.ui.fragment.AcgNewsMainFragment
import com.rabtman.common.base.SimpleActivity

/**
 * @author Rabtman
 */
class AcgNewsMainActivity : SimpleActivity() {

    @BindView(R2.id.layout_load_fragment)
    lateinit var layoutLoadFragment: FrameLayout
    override fun getLayoutId(): Int {
        return R.layout.activity_runalone
    }

    override fun initData() {
        val acgNewsMainFragment = AcgNewsMainFragment()
        loadRootFragment(R.id.layout_load_fragment, acgNewsMainFragment)
    }
}