package com.rabtman.acgschedule

import android.widget.FrameLayout
import butterknife.BindView
import com.rabtman.acgschedule.mvp.ui.fragment.ScheduleMainFragment
import com.rabtman.common.base.SimpleActivity

/**
 * @author Rabtman
 */
class AcgScheduleMainActivity : SimpleActivity() {
    @BindView(R2.id.layout_load_fragment)
    lateinit var layoutLoadFragment: FrameLayout

    override fun getLayoutId(): Int {
        return R.layout.activity_runalone
    }

    override fun initData() {
        val scheduleMainFragment = ScheduleMainFragment()
        loadRootFragment(R.id.layout_load_fragment, scheduleMainFragment)
    }
}