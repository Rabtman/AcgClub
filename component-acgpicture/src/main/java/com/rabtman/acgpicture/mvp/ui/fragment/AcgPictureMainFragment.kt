package com.rabtman.acgpicture.mvp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.tabs.TabLayout
import com.rabtman.acgpicture.R
import com.rabtman.acgpicture.R2
import com.rabtman.acgpicture.base.constant.IntentConstant
import com.rabtman.acgpicture.di.AcgPictureMainModule
import com.rabtman.acgpicture.di.DaggerAcgPictureMainComponent
import com.rabtman.acgpicture.mvp.AcgPictureMainContract
import com.rabtman.acgpicture.mvp.model.entity.AcgPictureType
import com.rabtman.acgpicture.mvp.presenter.AcgPictureMainPresenter
import com.rabtman.acgpicture.mvp.ui.adapter.AcgPictureMainPageAdapter
import com.rabtman.common.base.BaseFragment
import com.rabtman.common.di.component.AppComponent
import com.rabtman.router.RouterConstants
import java.util.*

/**
 * @author Rabtman
 */
@Route(path = RouterConstants.PATH_PICTURE_MAIN)
class AcgPictureMainFragment : BaseFragment<AcgPictureMainPresenter>(), AcgPictureMainContract.View {

    @BindView(R2.id.tab_picture)
    internal lateinit var mTabLayout: TabLayout
    @BindView(R2.id.vp_picture)
    internal lateinit var mViewPager: ViewPager

    internal var fragments: MutableList<Fragment> = ArrayList()
    internal lateinit var mAdapter: AcgPictureMainPageAdapter

    override fun getLayoutId(): Int {
        return R.layout.acgpicture_fragment_picture_main
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerAcgPictureMainComponent.builder()
                .appComponent(appComponent)
                .acgPictureMainModule(AcgPictureMainModule(this))
                .build()
                .inject(this)
    }

    override fun initData() {
        mPresenter.getAcgPictures()
    }

    override fun showPictureType(types: List<AcgPictureType>) {
        //animate-picture
        /*val animatePictureFragment = AnimatePictureFragment()
        fragments.add(animatePictureFragment)*/
        //a-picture
        /*val aPictureFragment = APictureFragment()
        fragments.add(aPictureFragment)*/
        //acg-picture
        for (picType in types) {
            val acgPictureItemFragment = AcgPictureItemFragment()
            val args = Bundle()
            args.putString(IntentConstant.ACGPICTURE_TYPE, picType.type)
            acgPictureItemFragment.arguments = args
            fragments.add(acgPictureItemFragment)
        }

        mAdapter = AcgPictureMainPageAdapter(mActivity.supportFragmentManager, fragments)
        mAdapter.setPictureTypes(types)
        mViewPager.adapter = mAdapter
        mViewPager.offscreenPageLimit = 1
        mTabLayout.setupWithViewPager(mViewPager)
    }
}
